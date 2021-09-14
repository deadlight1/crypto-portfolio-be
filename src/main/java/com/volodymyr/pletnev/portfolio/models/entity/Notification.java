package com.volodymyr.pletnev.portfolio.models.entity;

import com.volodymyr.pletnev.portfolio.models.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends AbstractEntity implements Serializable {
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "exchange_order_id")
	private ExchangeOrder exchangeOrder;
	@Digits(integer=12, fraction=20)
	private BigDecimal profitInPercent;
	@Digits(integer=12, fraction=20)
	private BigDecimal profit;
	@Digits(integer=12, fraction=20)
	private BigDecimal price;
	@Digits(integer=12, fraction=20)
	private BigDecimal percentChange1h;
	@Digits(integer=12, fraction=20)
	private BigDecimal percentChange24h;
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
}
