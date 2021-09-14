package com.volodymyr.pletnev.portfolio.controllers;


import com.volodymyr.pletnev.portfolio.models.dto.CountedOrder;
import com.volodymyr.pletnev.portfolio.models.dto.ExchangeOrderRequest;
import com.volodymyr.pletnev.portfolio.models.dto.PortfolioStatistic;
import com.volodymyr.pletnev.portfolio.models.entity.ExchangeOrder;
import com.volodymyr.pletnev.portfolio.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Map;

//todo delete cros origin and integrate frontend and backend
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
@PreAuthorize("hasRole('ROLE_USER')")
public class OrderController implements PageableController{

	private final OrderServiceImpl orderServiceImpl;



	@GetMapping
	public Page<CountedOrder> search(@RequestParam(value = "userId", required = false) String userId,
									 @RequestParam(value = "start", defaultValue = "0") @Min(0) @Max(9000) int start,
									 @RequestParam(value = "size", defaultValue = "-1") @Min(-1) @Max(1000) int size,
									 @RequestParam(value = "sort", defaultValue = "created") @Size(max = 32) String sort,
									 @RequestParam(value = "sortDir", defaultValue = "DESC") @Size(max = 5) String sortDir) {
		return orderServiceImpl.search(createPageable(start, size, sort, sortDir));
	}

	//todo move to anthor class
	@GetMapping("statistic")
	public PortfolioStatistic getPortfolioStatistic() {
		return orderServiceImpl.getPortfolioStatistic();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ExchangeOrder create(@RequestBody ExchangeOrderRequest exchangeOrderRequest) {
		return orderServiceImpl.create(exchangeOrderRequest);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") String id) {
		orderServiceImpl.delete(id);
	}

	@PutMapping("/{id}")
	public ExchangeOrder update(@PathVariable("id") String id, @RequestBody Map<String, Object> updates) {
		return orderServiceImpl.update(id, updates);
	}
}
