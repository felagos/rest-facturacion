package com.rest.facturacion.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.facturacion.dao.IUsuarioRepository;
import com.rest.facturacion.entities.Usuario;

@Service
public class JpaUserDetailService implements UserDetailsService {
	
	@Autowired
	private IUsuarioRepository usuarioDAO;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.usuarioDAO.findUsuarioByUsername(username);
		
		if(usuario == null)
			throw new UsernameNotFoundException("usuario no encontrado");
		
		List<GrantedAuthority> roles = usuario.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getNombreRol())).collect(Collectors.toList());
		
		if(roles.isEmpty())
			throw new UsernameNotFoundException("usuario no tiene roles asignados");
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, roles);
	}

}
