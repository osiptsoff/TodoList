package ru.todolist.rest.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.todolist.rest.security.jwt.JwtUtility;

@Component
public class JwtFilter extends GenericFilterBean {
	@Autowired
	private JwtUtility jwtUtility;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		var httpRequest = (HttpServletRequest)request;
		var httpResponse = (HttpServletResponse)response;
		
		String bearer = httpRequest.getHeader("Authorization");
		if(bearer == null || bearer.isEmpty() || !bearer.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}
		
		bearer = bearer.substring("Bearer ".length());
			
		if(!jwtUtility.validateAccessToken(bearer)) {
			httpResponse.setStatus(403);
			httpResponse.getWriter().write("Token is invalid.");
			//chain.doFilter(request, response);
			return;
		}
		Claims claims = jwtUtility.getAccessClaims(bearer);
		JwtAuthentication jwtAuthentication = jwtUtility.generateFromClaims(claims);
		jwtAuthentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
		
		chain.doFilter(request, response);
	}
}
