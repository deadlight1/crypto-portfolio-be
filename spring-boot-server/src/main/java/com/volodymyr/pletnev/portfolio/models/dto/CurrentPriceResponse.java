package com.volodymyr.pletnev.portfolio.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentPriceResponse {

	private String symbol;
	private BigDecimal price;
}
