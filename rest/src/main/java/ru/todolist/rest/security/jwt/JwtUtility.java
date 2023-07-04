package ru.todolist.rest.security.jwt;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ru.todolist.rest.security.JwtAuthentication;
import ru.todolist.rest.security.UserAuthority;
import ru.todolist.rest.security.UserDetailsImpl;

@Component
public class JwtUtility {
	private final long accessTokenLifespawn = 5 * 60 * 1000;
	private final long refreshTokenLifespawn = 7 * 60 * 60 * 1000;
	private final SecretKey accessSecret;
	private final SecretKey refreshSecret;
	
	public JwtUtility(
			@Value("${security.secret.access}") String accessSecret,
            @Value("${security.secret.refresh}") String refreshSecret) {
		this.accessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
		this.refreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret));
	}
	
	public String generateAccessToken(@NonNull UserDetailsImpl userDetails) {
		return Jwts.builder()
				.setSubject(Integer.toString(userDetails.getId()))
				.claim("roles", userDetails.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + accessTokenLifespawn))
				.signWith(accessSecret)
				.compact();
	}
	
	public String generateRefreshToken(@NonNull UserDetailsImpl userDetails) {
		return Jwts.builder()
			.setSubject(Integer.toString(userDetails.getId()))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + refreshTokenLifespawn))
			.signWith(refreshSecret)
			.compact();
	}
	
	public boolean validateAccessToken(String token) {
		return validateToken(token, accessSecret);
	}
	
	public boolean validateRefreshToken(String token) {
		return validateToken(token, refreshSecret);
	}
	
	
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, accessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, refreshSecret);
    }
    
    public JwtAuthentication generateFromClaims(Claims claims) {
    	JwtAuthentication auth = new JwtAuthentication();
    	auth.setIduser(Integer.parseInt(claims.getSubject()) );
    	auth.setAuthorities(Collections.singletonList((new UserAuthority())));
    	
    	return auth;
    }
    
	private boolean validateToken(@NonNull String token, @NonNull Key secret) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(secret)
			.build()
			.parseClaimsJws(token);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return true;
	}

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
