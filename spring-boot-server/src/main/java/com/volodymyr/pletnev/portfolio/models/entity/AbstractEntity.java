package com.volodymyr.pletnev.portfolio.models.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Generated
@MappedSuperclass
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(exclude = {"created", "updated"}, callSuper = false)
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6650501037450349223L;

	@Id
	@Builder.Default
	private String id = UUID.randomUUID().toString();

	protected LocalDateTime created;

	protected LocalDateTime updated;

	@PrePersist
	protected void onCreate() {
		updated = created = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updated = LocalDateTime.now();
	}
}

