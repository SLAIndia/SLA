package com.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppJwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_USER_OBJECT = "sub";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";

	private static final String AUDIENCE_UNKNOWN = "unknown";
	private static final String AUDIENCE_WEB = "web";
	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";

	/*
	 * @Value("${jwt.secret}") private String secret;
	 * 
	 * @Value("${jwt.expiration}") private Long expiration;
	 */

	private String secret = "554dddd";
	private Long expiration = 604800L;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = getClaimsFromToken(token);
			audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private String generateAudience(Device device) {
		String audience = AUDIENCE_UNKNOWN;
		if (device.isNormal()) {
			audience = AUDIENCE_WEB;
		} else if (device.isTablet()) {
			audience = AUDIENCE_TABLET;
		} else if (device.isMobile()) {
			audience = AUDIENCE_MOBILE;
		}
		return audience;
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
	}

	public String generateToken(UserDetails userDetails, Device device) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_USER_OBJECT, userDetails);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		AppAuthUser user = (AppAuthUser) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);
		// final Date expiration = getExpirationDateFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token)
				&& !isCreatedBeforeLastPasswordReset(created, null));
	}

	public AppAuthUser getUserDetailsFromToken(String token) {
		Claims claims;
		AppAuthUser appJwtUser;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			appJwtUser = (AppAuthUser) claims.get("CLAIM_KEY_USER_OBJECT");
		} catch (Exception e) {
			appJwtUser = null;
		}
		return appJwtUser;
	}
}