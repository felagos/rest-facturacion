package com.rest.facturacion.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.facturacion.auth.SimpleGrantedAuthorityMixin;
import com.rest.facturacion.services.interfaces.IJwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService implements IJwtService {

	@Value("${application.jwt.key}")
	private String key;

	@Value("${applicaion.jwt.expiration}")
	private Long expiration;

	@Override
	public String createToken(Authentication auth) throws IOException {
		Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

		Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

		String token = Jwts.builder().setClaims(claims).setSubject(auth.getName())
				.signWith(SignatureAlgorithm.HS512, this.key.getBytes()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + this.expiration)).compact();

		return token;
	}

	@Override
	public boolean validate(String token) {
		try {
			this.getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Claims getClaims(String token) {
		token = this.resolve(token);
		Claims claims = Jwts.parser().setSigningKey(this.key.getBytes()).parseClaimsJws(token).getBody();
		return claims;
	}

	@Override
	public String getUsername(String token) {
		return this.getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		Object roles = getClaims(token).get("authorities");

		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
						.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
		
		return authorities;
	}

	@Override
	public String resolve(String token) {
		return token.replace("Bearer ", "");
	}

}
