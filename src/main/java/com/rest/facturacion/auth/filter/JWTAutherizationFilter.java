package com.rest.facturacion.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.facturacion.auth.SimpleGrantedAuthorityMixin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAutherizationFilter extends BasicAuthenticationFilter {

	public JWTAutherizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader("Authorization");

		if (!requireAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}

		String token = request.getHeader("Authorization").replace("Bearer ", "");
		UsernamePasswordAuthenticationToken auth = null;

		try {
			Claims data = Jwts.parser().setSigningKey("eThWmZq4t6w9z$C&F)J@NcRfUjXn2r5u".getBytes())
					.parseClaimsJws(token).getBody();

			String username = data.getSubject();

			Collection<? extends GrantedAuthority> roles = Arrays
					.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
							.readValue(data.get("authorities").toString().getBytes(), SimpleGrantedAuthority[].class));
			
			auth = new UsernamePasswordAuthenticationToken(username, null, roles);	

		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			SecurityContextHolder.getContext().setAuthentication(auth);
			chain.doFilter(request, response);
		}

	}

	protected boolean requireAuthentication(String header) {
		if (header == null || !header.toLowerCase().startsWith("bearer"))
			return false;

		return true;
	}

}
