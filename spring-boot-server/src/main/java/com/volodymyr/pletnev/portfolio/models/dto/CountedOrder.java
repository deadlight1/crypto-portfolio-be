package com.volodymyr.pletnev.portfolio.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.volodymyr.pletnev.portfolio.models.entity.Coin;
import com.volodymyr.pletnev.portfolio.models.enums.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class CountedOrder implements Serializable {

	private String id;
	private LocalDateTime created;
	private BigDecimal amount;
	private BigDecimal averagePrice;
	private BigDecimal currentPrice;
	private BigDecimal quantity;
	private BigDecimal cost;
	private Integer margin;
	private OrderType orderType;
	private Coin coin;

	private BigDecimal profit;
	private BigDecimal profitInPercents;
}
