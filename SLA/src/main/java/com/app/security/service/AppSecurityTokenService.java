package com.app.security.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.app.security.AppAuthUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class AppSecurityTokenService {

	static final String CLAIM_KEY_ID = "id";
	static final String CLAIM_KEY_USERNAME = "username";
	static final String CLAIM_KEY_PASSWORD = "password";
	static final String CLAIM_KEY_AUTHORITIES = "authorities";
	static final String CLAIM_KEY_STATUS = "status";

	static final String CLAIM_KEY_CREATED = "created";

	private String secret = "secret43046789";
	private Long expiration = 604800L;

	public String generateToken(AppAuthUser appJwtUser) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_ID, appJwtUser.getId());
		claims.put(CLAIM_KEY_USERNAME, appJwtUser.getUsername());
		claims.put(CLAIM_KEY_PASSWORD, appJwtUser.getPassword());
		List<String> authorities = appJwtUser.getAuthorities().stream().map(authority -> authority.toString().trim())
				.collect(Collectors.toList());
		claims.put(CLAIM_KEY_AUTHORITIES, authorities);
		claims.put(CLAIM_KEY_STATUS, appJwtUser.isEnabled());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	@SuppressWarnings("unchecked")
	public Optional<AppAuthUser> verify(String token) throws IOException, URISyntaxException {

		Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		Claims c = claims.getBody();
		AppAuthUser appAuthUser = new AppAuthUser(Long.parseLong(c.get(CLAIM_KEY_ID).toString()),
				c.get(CLAIM_KEY_USERNAME).toString(), 
				c.get(CLAIM_KEY_PASSWORD).toString(),
				(ArrayList<String>) c.get(CLAIM_KEY_AUTHORITIES),
				Boolean.parseBoolean(c.get(CLAIM_KEY_STATUS).toString()));

		return Optional.of(appAuthUser);
	}

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

}
