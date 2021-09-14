package com.volodymyr.pletnev.portfolio.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coin implements Serializable {
	@Id
	private Long id;
	@Column(updatable = false)
	private LocalDateTime created = LocalDateTime.now();
	private LocalDateTime updated;
	private String name;
	private String symbol;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal price;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal volume24h;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal percentChange1h;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal percentChange24h;

	public Coin(Long id, String name, String symbol, @Digits(integer = 12, fraction = 20) BigDecimal price, @Digits(integer = 12, fraction = 20) BigDecimal volume_24h, @Digits(integer = 12, fraction = 20) BigDecimal percentChange1h, @Digits(integer = 12, fraction = 20) BigDecimal percentChange24h) {
		this.id = id;
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.volume24h = volume_24h;
		this.percentChange1h = percentChange1h;
		this.percentChange24h = percentChange24h;
	}

	@PreUpdate
	public void changeUpdateTime() {
		updated = LocalDateTime.now();
	}
}
