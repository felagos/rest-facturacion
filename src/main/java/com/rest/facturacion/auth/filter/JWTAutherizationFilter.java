package com.rest.facturacion.auth.filter;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.rest.facturacion.services.interfaces.IJwtService;

import io.jsonwebtoken.JwtException;


public class JWTAutherizationFilter extends BasicAuthenticationFilter {
	
	private IJwtService jwtService;

	public JWTAutherizationFilter(AuthenticationManager authenticationManager, IJwtService jwtService) {
		super(authenticationManager);
		this.jwtService = jwtService;
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

			String username = this.jwtService.getUsername(token);
			Collection<? extends GrantedAuthority> roles = this.jwtService.getRoles(token);

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
