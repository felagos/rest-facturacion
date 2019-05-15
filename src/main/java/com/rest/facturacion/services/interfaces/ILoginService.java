package com.rest.facturacion.services.interfaces;

import com.rest.facturacion.entities.Usuario;

public interface ILoginService {
	
	public void registerUser(Usuario usuario);
	
	public boolean existsUsername(String username);
	
	public void doLogin(String username, String password);

}
