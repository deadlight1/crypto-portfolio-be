package com.volodymyr.pletnev.portfolio.service;


import com.volodymyr.pletnev.portfolio.models.dto.CountedOrder;
import com.volodymyr.pletnev.portfolio.models.dto.MostProfitableCoinsResponse;
import com.volodymyr.pletnev.portfolio.models.dto.PortfolioStatistic;
import com.volodymyr.pletnev.portfolio.models.entity.ExchangeOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {

	private final OrderServiceImpl orderService;

	@Transactional(readOnly = true)
	public PortfolioStatistic getPortfolioStatistic() {
		List<ExchangeOrder> pagedOrders = orderService.findAllExchangeOrders();
		List<CountedOrder> countedOrders = pagedOrders.stream()
				.map(this.orderService::countOrder)
				.collect(Collectors.toList());
		PortfolioStatistic portfolioStatistic = new PortfolioStatistic();
		for (CountedOrder countedOrder : countedOrders) {
			portfolioStatistic.setProfit(portfolioStatistic.getProfit().add(countedOrder.getProfit()));
			portfolioStatistic.setStartPrice(portfolioStatistic.getStartPrice().add(countedOrder.getAmount()));
			portfolioStatistic.setCurrentPrice(portfolioStatistic.getCurrentPrice().add(countedOrder.getCost()));
		}
		return portfolioStatistic;
	}

	@Transactional(readOnly = true)
	public List<MostProfitableCoinsResponse> getMostProfitableCoins() {
		List<ExchangeOrder> exchangeOrderList = orderService.findAllByUserId();
		log.info("All Exchange Orders {}", exchangeOrderList.toString());

		List<CountedOrder> countedOrderList = exchangeOrderList.stream()
				.map(orderService::countOrder)
				.collect(Collectors.toList());
		log.info("All Counted Orders {}", countedOrderList.toString());

		Map<String, BigDecimal> map = new HashMap<>();
		for (CountedOrder countedOrder : countedOrderList) {
			if (map.containsKey(countedOrder.getCoin().getName())) {
				BigDecimal newProfit = map.get(countedOrder.getCoin().getName()).add(countedOrder.getProfit());
				map.put(countedOrder.getCoin().getName(), newProfit);
			} else {
				map.put(countedOrder.getCoin().getName(), countedOrder.getProfit());
			}
		}

		List<MostProfitableCoinsResponse> mostProfitableCoinsResponseList = new ArrayList<>();
		for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
			MostProfitableCoinsResponse mostProfitableCoinsResponse = new MostProfitableCoinsResponse();
			mostProfitableCoinsResponse.setName(entry.getKey());
			mostProfitableCoinsResponse.setProfit(entry.getValue());
			mostProfitableCoinsResponseList.add(mostProfitableCoinsResponse);
		}
		log.info("Most Profitable Coins {}", mostProfitableCoinsResponseList);
		return mostProfitableCoinsResponseList;
	}

	@Transactional(readOnly = true)
	public List<MostProfitableCoinsResponse> getMostProfitableOrders() {
		List<ExchangeOrder> exchangeOrderList = orderService.findAllByUserId();
		log.info("All Exchange Orders {}", exchangeOrderList.toString());
		List<CountedOrder> countedOrderList = exchangeOrderList.stream()
				.map(orderService::countOrder)
				.collect(Collectors.toList());
		List<MostProfitableCoinsResponse> mostProfitableCoinsResponses = new ArrayList<>();
		for (CountedOrder countedOrder : countedOrderList) {
			MostProfitableCoinsResponse mostProfitableCoinsResponse = new MostProfitableCoinsResponse();
			mostProfitableCoinsResponse.setName(String.format("Name %s Id (%s)", countedOrder.getCoin().getName(),
					countedOrder.getId()));
			mostProfitableCoinsResponse.setProfit(countedOrder.getProfit());
			mostProfitableCoinsResponses.add(mostProfitableCoinsResponse);
		}
		return mostProfitableCoinsResponses;

	}
}
