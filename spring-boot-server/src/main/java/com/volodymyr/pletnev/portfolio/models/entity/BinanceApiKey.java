package com.volodymyr.pletnev.portfolio.models.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
@Generated
@Entity
@Table(name = "binance_api_key")
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class BinanceApiKey extends AbstractEntity implements Serializable {
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	private String apiKey;
	private String secretKey;
}
