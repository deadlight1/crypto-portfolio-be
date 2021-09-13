package com.volodymyr.pletnev.portfolio.models.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Validated
@Generated
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractDto implements Serializable {

	private static final long serialVersionUID = 6650501037450349223L;

	@Builder.Default
	private String id = UUID.randomUUID().toString();

	protected LocalDateTime created;

	protected LocalDateTime updated;
}
