package com.app.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.usermanagement.entity.UserDetailsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AppAuthUser implements UserDetails {

	private static final long serialVersionUID = -3124445235780011074L;
	private final Long id;
	private final String username;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final boolean enabled;

	public AppAuthUser(Long id, String username, String password, List<String> authorities, boolean enabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = Collections.singletonList(new SimpleGrantedAuthority(authorities.get(0).toString()));

		this.enabled = enabled;
	}

	public AppAuthUser(UserDetailsEntity userDetailsEntity) {
		this.id = userDetailsEntity.getUser().getId().longValue();
		this.username = userDetailsEntity.getUser().getUsername();
		this.password = userDetailsEntity.getUser().getPassword();

		this.authorities = Collections
				.singletonList(new SimpleGrantedAuthority(userDetailsEntity.getUser().getRole().getRolename()));
		this.enabled = userDetailsEntity.getUser().getUserStatus() == 1;

	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
