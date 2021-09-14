package com.volodymyr.pletnev.portfolio.controllers;


import com.volodymyr.pletnev.portfolio.models.dto.MostProfitableCoinsResponse;
import com.volodymyr.pletnev.portfolio.models.dto.PortfolioStatistic;
import com.volodymyr.pletnev.portfolio.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//todo delete cros origin and integrate frontend and backend
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/statistic")
@PreAuthorize("hasRole('ROLE_USER')")
public class StatisticController {

	private final StatisticService statisticController;

	@GetMapping("most_profitable")
	public List<MostProfitableCoinsResponse> getMostProfitableCoins(){
		return statisticController.getMostProfitableCoins();
	}

	@GetMapping("most_profitable_orders")
	public List<MostProfitableCoinsResponse> getMostProfitableOrders(){
		return statisticController.getMostProfitableOrders();
	}

	@GetMapping("portfolio_statistic")
	public PortfolioStatistic getPortfolioStatistic(){
		return statisticController.getPortfolioStatistic();
	}
}
