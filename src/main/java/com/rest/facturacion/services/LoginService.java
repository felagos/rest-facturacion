package com.rest.facturacion.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.facturacion.dao.IUsuarioRepository;
import com.rest.facturacion.entities.Rol;
import com.rest.facturacion.entities.Usuario;
import com.rest.facturacion.services.interfaces.ILoginService;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private IUsuarioRepository usuarioDAO;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private JpaUserDetailService userDetailService;

	/*@Autowired
	private AuthenticationManager authenticationManager;*/

	@Autowired
	private HttpServletRequest request;

	@Override
	@Transactional
	public void registerUser(Usuario usuario) {
		Rol rol = new Rol();
		rol.setNombreRol("ROL_USER");
		usuario.getRoles().add(rol);

		String password = usuario.getPassword();
		String passBcrypt = encoder.encode(password);
		usuario.setPassword(passBcrypt);

		this.usuarioDAO.save(usuario);
		
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsUsername(String username) {
		return this.usuarioDAO.findUsuarioByUsername(username) != null;
	}

	@Override
	public void doLogin(String username, String password) {
		/*UserDetails userDetail = this.userDetailService.loadUserByUsername(username);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetail, password,
				userDetail.getAuthorities());

		request.getSession();
		token.setDetails(new WebAuthenticationDetails(request));

		Authentication authenticatedUser = authenticationManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);*/

	}

}
