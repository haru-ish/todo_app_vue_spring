package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class LoginFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fil) throws ServletException, IOException {
		
		// Get token from header
		String header = req.getHeader("X-AUTH-TOKEN");
		
		if(header == null || !header.startsWith("Bearer ")) {
			fil.doFilter(req, res);
			return;
		}
		
		String token = header.substring(7);
		// Token verification and authentication.
		DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256("secret")).build().verify(token);
		// Get username
		String username = decodedJWT.getClaim("username").asString();
		// Setting login status
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
		fil.doFilter(req, res);
		
	}
}
