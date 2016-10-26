package com.app.security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AppJwtAuthenticatedProfile implements Authentication {

	private final AppJwtUser appJwtUser;

	public AppJwtAuthenticatedProfile(AppJwtUser appJwtUser) {
		this.appJwtUser = appJwtUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return appJwtUser.getAuthorities().stream().map(s -> new SimpleGrantedAuthority(s.getAuthority()))
				.collect(Collectors.toList());

	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return appJwtUser.getUsername();
	}
}
