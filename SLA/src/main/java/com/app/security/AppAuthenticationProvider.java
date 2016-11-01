package com.app.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.app.security.exceptions.AppAuthenticationException;
import com.app.security.service.AppSecurityTokenService;

@Component
public class AppAuthenticationProvider implements AuthenticationProvider {

	private final AppSecurityTokenService securityTokenService;

	@SuppressWarnings("unused")
	public AppAuthenticationProvider() {
		this(null);
	}

	@Autowired
	public AppAuthenticationProvider(AppSecurityTokenService securityTokenService) {
		this.securityTokenService = securityTokenService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			Optional<AppAuthUser> appJwtUser = securityTokenService.verify((String) authentication.getCredentials());
			return new AppAuthenticatedProfile(appJwtUser.get());

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppAuthenticationException("Failed to verify token", e);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AppAuthToken.class.equals(authentication);
	}
}
