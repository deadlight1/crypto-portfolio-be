package com.volodymyr.pletnev.portfolio.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Generated
@Entity
@Table(name = "usr")
@Data
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {


	@NotBlank
	@Size(max = 50)
	@Email
	@Column(unique = true)
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	private boolean isAccountNonExpired = true;

	private boolean isAccountNonLocked = true;

	private boolean isCredentialsNonExpired = true;

	private boolean isEnabled = true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> authorities = new HashSet<>();

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public User( String email, String password,
						   Set<Role> authorities) {

		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	/*public static User build(User user) {
		List<GrantedAuthority> authorities = user.getAuthorities().stream()
				.map(role -> new SimpleGrantedAuthority(role.getAuthority()))
				.collect(Collectors.toList());

		return new User(
				user.getId(),
				user.getEmail(),
				user.getPassword(),
				authorities);
	}*/

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
}
