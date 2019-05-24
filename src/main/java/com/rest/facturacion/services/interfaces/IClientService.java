package com.rest.facturacion.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rest.facturacion.entities.Cliente;
import com.rest.facturacion.exceptions.NotFoundException;

public interface IClientService {

	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public void save(Cliente cliente);
	public Cliente findOne(Long id) throws NotFoundException;
	public void delete(Long id);
	public Cliente fetchClientWithFacturaById(Long id);
	public boolean existsClientId(Long id);	
}
