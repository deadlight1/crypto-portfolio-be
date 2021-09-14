package com.volodymyr.pletnev.portfolio.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioStatistic {

	private BigDecimal startPrice = new BigDecimal("0");
	private BigDecimal currentPrice = new BigDecimal("0");
	private BigDecimal profit = new BigDecimal("0");
}
