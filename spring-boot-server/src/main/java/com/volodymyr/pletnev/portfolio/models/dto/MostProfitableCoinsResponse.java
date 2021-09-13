package com.volodymyr.pletnev.portfolio.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostProfitableCoinsResponse implements Serializable {
	private String name;
	private BigDecimal profit;
	private BigDecimal profitInPercent;
}
