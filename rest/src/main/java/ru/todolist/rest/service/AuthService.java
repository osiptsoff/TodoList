package ru.todolist.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.security.auth.message.AuthException;
import ru.todolist.rest.model.User;
import ru.todolist.rest.repository.UserDAO;
import ru.todolist.rest.security.UserDetailsImpl;
import ru.todolist.rest.security.jwt.AuthRequest;
import ru.todolist.rest.security.jwt.AuthResponse;
import ru.todolist.rest.security.jwt.JwtUtility;
import ru.todolist.rest.security.jwt.RefreshRequest;

@Service
public class AuthService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private JwtUtility jwtUtility;
	
	public AuthResponse authenticate(AuthRequest authRequest) throws DataAccessException, UsernameNotFoundException, BadCredentialsException {
		UserDetailsImpl userDetails = (UserDetailsImpl) userDao.loadUserByUsername(authRequest.getLogin());
		
		if (!passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword()))
			throw new BadCredentialsException("Password does not match.");
		
		var response = new AuthResponse();
		response.setAccessToken(jwtUtility.generateAccessToken(userDetails));
		response.setRefreshToken(jwtUtility.generateRefreshToken(userDetails));
		
		return response;
	}
	
	public String refresh(RefreshRequest refreshRequest) throws AuthException, NumberFormatException {
		String refreshToken = refreshRequest.getRefreshToken();
		if(!jwtUtility.validateRefreshToken(refreshToken))
			throw new AuthException("Invalid refresh token.");
		
		int iduser = Integer.parseInt(jwtUtility.getRefreshClaims(refreshToken).getSubject());
		User user = new User();
		user.setIduser(iduser);
		
		return jwtUtility.generateAccessToken(new UserDetailsImpl(user));
	}
}
