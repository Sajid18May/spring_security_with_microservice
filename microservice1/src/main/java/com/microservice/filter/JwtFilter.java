package com.microservice.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microservice.client.AuthFeignClient;
import com.microservice.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtService jwtService;
	@Autowired
	AuthFeignClient authFeignClient;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader=request.getHeader("Authorization");
		if(authHeader!=null && authHeader.startsWith("Bearer ")) {
			String jwtToken=authHeader.substring(7);
			String username=jwtService.validateTokenAndGetSubject(jwtToken);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				var user=authFeignClient.getUserByUsername(username, jwtToken);
				var authToken=new UsernamePasswordAuthenticationToken(user, null,Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			}
		}
		filterChain.doFilter(request, response);
		
	}
	
}
