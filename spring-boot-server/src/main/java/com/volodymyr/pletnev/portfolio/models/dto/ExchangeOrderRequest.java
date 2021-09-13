package com.volodymyr.pletnev.portfolio.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Validated
@Generated
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder
@NoArgsConstructor
public class ExchangeOrderRequest extends AbstractDto  implements Serializable {
	@NotBlank
	private String name;
	@NotNull
	private Long coinId;
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal amount;
	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal averagePrice;
	private Integer margin;
}
