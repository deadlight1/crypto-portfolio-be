package com.volodymyr.pletnev.portfolio.models.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role extends AbstractEntity implements GrantedAuthority {

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	@Override
	public String getAuthority() {
		return name.name();
	}
}