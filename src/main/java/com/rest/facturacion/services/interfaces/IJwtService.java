package com.rest.facturacion.services.interfaces;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface IJwtService {
	
	public String createToken(Authentication auth) throws IOException;
	
	public boolean validate(String token);
	
	public Claims getClaims(String token);
	
	public String getUsername(String token);
	
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;
	
	public String resolve(String token);

}
