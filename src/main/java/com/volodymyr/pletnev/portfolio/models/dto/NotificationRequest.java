package com.volodymyr.pletnev.portfolio.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.volodymyr.pletnev.portfolio.models.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Generated
@Validated
@Data
@Builder
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
	private String orderId;
	private Long coinId;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal profitInPercent;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal profit;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal price;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal percentChange1h;
	@Digits(integer = 12, fraction = 20)
	private BigDecimal percentChange24h;
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;
}
