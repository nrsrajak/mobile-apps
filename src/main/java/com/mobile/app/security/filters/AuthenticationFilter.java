package com.mobile.app.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobile.app.SpringApplicationContext;
import com.mobile.app.security.SecurityConstants;
import com.mobile.app.service.UserService;
import com.mobile.app.shared.dto.UserDTO;
import com.mobile.app.ui.controller.model.request.UserLoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}
	
//	The method will triggered when username and password provided by the user.
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		try {
			UserLoginRequestModel requestModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							requestModel.getEmail(),
							requestModel.getPassword(),
							new ArrayList<>())
					);                                                                                         
		}catch (IOException e) {
			throw new RuntimeException();
		}
		
		
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain filter,
											Authentication auth) throws IOException, ServletException {
		
		String username = ((User) auth.getPrincipal()).getUsername();
		
		String token = Jwts.builder().
				setSubject(username).
				setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME)).
				signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
				.compact();
		
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		
		UserDTO userDTO = userService.getUser(username);
		
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+token);
		response.addHeader("User Id", userDTO.getUserId());
	}

}
