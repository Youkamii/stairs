package com.sparta.stairs.auth.jwt;

import com.sparta.stairs.user.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j(topic = "JwtUil")
public class JwtUtil {

	public static final String AUTHORIZATION_KEY = "auth";
	public static final String BEARER_PREFIX = "Bearer ";
	public static final long ACCESS_TOKEN_TIME  = 60 * 60 * 1000L;
	public static final String ACCESS_TOKEN_HEADER = "Authorization";
	public static final long REFRESH_TOKEN_TIME  = 3 * 60 * 60 * 1000L;
	public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";

	@Value("${jwt.secret.key}")
	private String secretKey;

	private Key key;

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		key = Keys.hmacShaKeyFor(bytes);
	}

	public String resolveToken(HttpServletRequest request, String header) {
		String bearerToken = request.getHeader(header);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}
	public String createAccessToken(String username, UserRoleEnum role) {
		return this.createToken(username, role, ACCESS_TOKEN_TIME);
	}

	public String createRefreshToken(String username, UserRoleEnum role) {
		return this.createToken(username, role, REFRESH_TOKEN_TIME);
	}

	private String createToken(String username, UserRoleEnum role, long expiration) {
		Date date = new Date();

		return BEARER_PREFIX +
				Jwts.builder()
						.setSubject(username)
						.setExpiration(new Date(date.getTime() + expiration))
						.claim(AUTHORIZATION_KEY, role)
						.setIssuedAt(date)
						.signWith(key, signatureAlgorithm)
						.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			log.error("Expired JWT token, 만료된 JWT token 입니다.");
			return false;
		}
	}

	public Claims getUserInfoFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public UserRoleEnum getUserRole(Claims claims) {
		return UserRoleEnum.valueOf(claims.get(AUTHORIZATION_KEY, String.class));
	}
}
