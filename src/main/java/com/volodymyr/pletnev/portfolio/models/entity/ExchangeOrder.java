package com.volodymyr.pletnev.portfolio.models.entity;

import com.volodymyr.pletnev.portfolio.models.enums.OrderType;
import com.volodymyr.pletnev.portfolio.util.Updatable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

@Generated
@Entity
@Table(name = "exchange_order")
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ExchangeOrder extends AbstractEntity implements Serializable {

	@Updatable
	private String name;
	@Digits(integer=12, fraction=20)
	@Updatable
	private BigDecimal amount;
	@Digits(integer=12, fraction=20)
	@Updatable
	private BigDecimal averagePrice;
	@Updatable
	private Integer margin;
	@Updatable
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	@ManyToOne
	private User user;
	@ManyToOne
	private Coin coin;

}
