package com.volodymyr.pletnev.portfolio.service;

import com.volodymyr.pletnev.portfolio.models.dto.CountedOrder;
import com.volodymyr.pletnev.portfolio.models.dto.NotificationRequest;
import com.volodymyr.pletnev.portfolio.models.dto.NotificationToUserRequest;
import com.volodymyr.pletnev.portfolio.models.entity.ExchangeOrder;
import com.volodymyr.pletnev.portfolio.models.entity.Notification;
import com.volodymyr.pletnev.portfolio.repository.NotificationRepository;
import com.volodymyr.pletnev.portfolio.util.CryptoPortfolioModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService extends PartialUpdatingService {

	private static final CryptoPortfolioModelMapper MODEL_MAPPER = CryptoPortfolioModelMapper.INSTANCE;

	// todo refactor to service layer
	private final NotificationRepository notificationRepository;
	private final OrderServiceImpl orderService;
	private final RestTemplate restTemplate;

	@Transactional
	public Notification create(NotificationRequest notificationRequest) {
		Notification notification = MODEL_MAPPER.toEntity(notificationRequest);
		ExchangeOrder exchangeOrder = orderService.getById(notificationRequest.getOrderId());
		notification.setExchangeOrder(exchangeOrder);
		return notificationRepository.save(notification);
	}

	@Transactional
	public Notification patch(String id, Map<String,Object> updates) {
		Notification notification = applyUpdates(getById(id), updates);
		return notificationRepository.save(notification);
	}

	@Transactional
	public void delete(String id) {
		notificationRepository.deleteById(id);
	}

	@Transactional
	public Notification getById(String id) {
		return notificationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));
	}

	//todo
	@Scheduled(fixedDelay = 1000)
	@Transactional
	public void notifyUser() {
		List<Notification> notifications = notificationRepository.findAll();
		for (Notification notification : notifications) {
			CountedOrder countedOrder = orderService.countOrder(notification.getExchangeOrder());
			NotificationToUserRequest notificationToUserRequest = new NotificationToUserRequest();
			if (notification.getProfit() != null && countedOrder.getProfit().compareTo(notification.getProfit()) > 0) {
				notificationToUserRequest.setProfit(countedOrder.getProfit());
				notificationToUserRequest.setEmpty(false);
			}
			if (notification.getProfitInPercent() != null && countedOrder.getProfitInPercents().compareTo(notification.getProfitInPercent()) > 0) {
				notificationToUserRequest.setProfitInPercent(countedOrder.getProfitInPercents());
				notificationToUserRequest.setEmpty(false);
			}
			if (notification.getPrice() != null && countedOrder.getCoin().getPrice().compareTo(notification.getPrice()) > 0) {
				notificationToUserRequest.setPrice(countedOrder.getCoin().getPrice());
				notificationToUserRequest.setEmpty(false);
			}
			notificationToUserRequest.setCoinSymbol(countedOrder.getCoin().getSymbol());
			notificationToUserRequest.setCoinName(countedOrder.getCoin().getName());
			if (!notificationToUserRequest.isEmpty()) {
				ResponseEntity<NotificationRequest> responseEntity = restTemplate.postForEntity("http://localhost:8899/api/notification",
						notificationToUserRequest,
						NotificationRequest.class);
				if (responseEntity.getStatusCode().is2xxSuccessful()) {
					notificationRepository.delete(notification);
				}
			}
		}
	}

	@Transactional
	public void deleteAllByOrder(String  exchangeOrderId){
		notificationRepository.deleteAllByExchangeOrderId(exchangeOrderId);
	}
}
