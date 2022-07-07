package com.example.demo.config;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.form.UserForm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		
		// Setting AuthenticationManager
		this.authenticationManager = authenticationManager;
		
		// Setting login path
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
		// Setting parameter for login
		setUsernameParameter("username");
		setPasswordParameter("password");
		
		// When login success, publish token and set it to response
		this.setAuthenticationSuccessHandler((req, res, ex) -> {
			// Create token
			String token = JWT.create()
					.withIssuer("com.example.demo") // Publisher
					.withClaim("username", ex.getName()) // Setting value for key
					.sign(Algorithm.HMAC256("secret")); // Create JWT
			res.setHeader("X-AUTH-TOKEN", token); // Setting token for key(X-AUTH-TOKEN)
			res.setStatus(200);
		});
		
		// When login fail
		this.setAuthenticationFailureHandler((req, res, ex) -> {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		});	
	}
	
	// Authentication process
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			ServletInputStream stream = req.getInputStream();
			// Read user information from request parameters
			UserForm userForm = new ObjectMapper().readValue(req.getInputStream(), UserForm.class);
			return this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userForm.getUserName(), userForm.getPassword(), new ArrayList<>())
					);	
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}
}
