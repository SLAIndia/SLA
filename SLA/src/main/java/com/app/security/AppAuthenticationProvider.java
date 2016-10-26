package com.app.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.app.security.exceptions.AppAuthenticationException;
/*import com.jwtmyapp.security.model.MinimalProfile;
import com.jwtmyapp.security.service.AppJwtService;*/

@Component
public class AppAuthenticationProvider implements AuthenticationProvider {
	
	//private final AppJwtService appJwtService;
	
 
	private final AppJwtTokenUtil appJwtTokenUtil;

	@SuppressWarnings("unused")
	public AppAuthenticationProvider() {
		this(null);
	}

	@Autowired
	public AppAuthenticationProvider(AppJwtTokenUtil appJwtTokenUtil) {
		this.appJwtTokenUtil = appJwtTokenUtil;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			//Optional<MinimalProfile> possibleProfile = appJwtService.verify((String) authentication.getCredentials());
			//return new AppJwtAuthenticatedProfile(possibleProfile.get());
			return null;
		} catch (Exception e) {
			throw new AppAuthenticationException("Failed to verify token", e);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AppAuthToken.class.equals(authentication);
	}
}
