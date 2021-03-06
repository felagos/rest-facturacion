package com.rest.facturacion.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rest.facturacion.dao.IClienteRepository;
import com.rest.facturacion.entities.Cliente;
import com.rest.facturacion.exceptions.NotFoundException;
import com.rest.facturacion.services.interfaces.IClientService;

@Service
public class ClienteService implements IClientService {
	
	@Autowired
	private IClienteRepository clienteDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) this.clienteDAO.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		this.clienteDAO.save(cliente);
	}
	
	@Override
	@Transactional
	public boolean existsClientId(Long id) {
		return this.clienteDAO.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) throws NotFoundException {
		boolean exists = this.clienteDAO.findById(id).isPresent();
		if(!exists) throw new NotFoundException("NOT FOUND");
		
		return this.clienteDAO.findById(id).get();
	}

	@Override
	@Transactional
	public void delete(Long id) throws NotFoundException {
		boolean exists = this.clienteDAO.findById(id).isPresent();
		if(!exists) throw new NotFoundException("NOT FOUND");
		
		this.clienteDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {	
		return this.clienteDAO.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente fetchClientWithFacturaById(Long id) {
		return this.clienteDAO.fetchClientWithFacturaById(id);
	}

}
