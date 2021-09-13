package com.volodymyr.pletnev.portfolio.service;


import static com.volodymyr.pletnev.portfolio.util.SecurityUtility.currentId;
import static com.volodymyr.pletnev.portfolio.util.SecurityUtility.getSecurityPrincipal;
import static java.math.BigDecimal.ROUND_DOWN;

import com.volodymyr.pletnev.portfolio.models.dto.CountedOrder;
import com.volodymyr.pletnev.portfolio.models.dto.ExchangeOrderRequest;
import com.volodymyr.pletnev.portfolio.models.dto.PortfolioStatistic;
import com.volodymyr.pletnev.portfolio.models.entity.Coin;
import com.volodymyr.pletnev.portfolio.models.entity.ExchangeOrder;
import com.volodymyr.pletnev.portfolio.models.entity.Notification;
import com.volodymyr.pletnev.portfolio.models.entity.User;
import com.volodymyr.pletnev.portfolio.repository.CoinRepository;
import com.volodymyr.pletnev.portfolio.repository.NotificationRepository;
import com.volodymyr.pletnev.portfolio.repository.OrderRepository;
import com.volodymyr.pletnev.portfolio.repository.UserRepository;
import com.volodymyr.pletnev.portfolio.util.CryptoPortfolioModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

// todo add transactional and refactor
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends PartialUpdatingService {

	private final OrderRepository orderRepository;
	private final NotificationRepository notificationRepository;
	private final CoinRepository coinRepository;
	private final UserRepository userRepository;

	private static final CryptoPortfolioModelMapper MODEL_MAPPER = CryptoPortfolioModelMapper.INSTANCE;


	public Page<CountedOrder> search(Pageable pageable) {
		Page<ExchangeOrder> pagedOrders = orderRepository.findAllByUser_Id(pageable, currentId());
		return pagedOrders
				.map(this::countOrder);
	}

	//todo move to another service and DRY

	@Transactional(readOnly = true)
	public List<ExchangeOrder> findAllExchangeOrders() {
		return orderRepository.findAllByUser_Id(currentId());
	}

	@Transactional
	public ExchangeOrder create(ExchangeOrderRequest exchangeOrderRequest) {
		ExchangeOrder exchangeOrder = MODEL_MAPPER.toEntity(exchangeOrderRequest);
		Coin coin = coinRepository.findById(exchangeOrderRequest.getCoinId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coin not found"));
		User user = userRepository.findById(getSecurityPrincipal().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
		exchangeOrder.setUser(user);
		exchangeOrder.setCoin(coin);
		return orderRepository.save(exchangeOrder);
	}

	@Transactional
	public CountedOrder countOrder(ExchangeOrder order) {
		try {
			CountedOrder countedOrder = new CountedOrder();
			if (order.getMargin() > 0) {
				order.setAmount(order.getAmount().multiply(BigDecimal.valueOf(order.getMargin())));
				countedOrder.setMargin(order.getMargin());
			}
			countedOrder.setCoin(order.getCoin());
			countedOrder.setAmount(order.getAmount());
			countedOrder.setAveragePrice(order.getAveragePrice());
			Coin coin = getCurrentPrice(order.getCoin().getId());
			BigDecimal quantity = countQuantity(order.getAmount(), order.getAveragePrice());
			BigDecimal cost = countCost(quantity, coin.getPrice());

			countedOrder.setCurrentPrice(coin.getPrice());
			countedOrder.setQuantity(quantity);
			countedOrder.setCost(cost);

			BigDecimal profit = countProfit(countedOrder.getAmount(), cost);

			countedOrder.setProfit(profit.negate());
			countedOrder.setId(order.getId());
			countedOrder.setCreated(order.getCreated());
			BigDecimal delimeter = countedOrder.getAmount().divide(BigDecimal.valueOf(100), 2, ROUND_DOWN);
			System.out.println(delimeter);
			countedOrder.setProfitInPercents(profit.divide(delimeter, 2, ROUND_DOWN).negate());

			log.info("Current price of {}: {}, {}", coin.getSymbol(),
					coin.getPrice(),
					countedOrder);
			return countedOrder;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Something went wrong in calculations");
		}
	}

	@Transactional(readOnly = true)
	public Coin getCurrentPrice(Long id) {
		return coinRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
	}


	/*	public List<Coin> getAllCoinsByNames(List<String> names) {
	 *//*LocalDateTime updated = coinRepository.findFirstAny();
		if(updated.isAfter(LocalDateTime.now().minusMinutes(10))){
			return loadAllCoinPricesFromBinance()
			.stream().filter(coin -> names.contains(coin.getSymbol()))
			.collect(Collectors.toList());
		}else {*//*
		return coinRepository.findAllBySymbolIn(names);
		*//*}*//*
	}*/


	public List<Coin> loadAllCoinPricesWichIn(List<String> names) {
		return coinRepository.findAllBySymbolIn(names);
	}

	private BigDecimal countQuantity(BigDecimal amount, BigDecimal averagePrice) {
		return amount.divide(averagePrice, 10, ROUND_DOWN);
	}

	private BigDecimal countCost(BigDecimal quantity, BigDecimal currentPrice) {
		return quantity.multiply(currentPrice);
	}

	private BigDecimal countProfit(BigDecimal amount, BigDecimal cost) {
		return amount.subtract(cost);
	}

	@Transactional
	public void delete(String id) {
		List<Notification> notifications = notificationRepository.findAllByExchangeOrderId(id);
		notifications.forEach(notification -> {
			notification.setExchangeOrder(null);
			notification.setUser(null);
		});
		notificationRepository.saveAll(notifications);
		notificationRepository.deleteAllByExchangeOrderId(id);
		orderRepository.deleteById(id);
	}

	@Transactional
	public ExchangeOrder getById(String id) {
		return orderRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));
	}

	@Transactional
	public ExchangeOrder update(String id, Map<String, Object> updates) {
		ExchangeOrder exchangeOrder = applyUpdates(getById(id), updates);
		return orderRepository.save(exchangeOrder);
	}

	@Transactional
	public PortfolioStatistic getPortfolioStatistic() {
		return null;
	}

	@Transactional
	public List<ExchangeOrder> findAllByUserId() {
		return orderRepository.findAllByUser_Id(currentId());
	}
}
