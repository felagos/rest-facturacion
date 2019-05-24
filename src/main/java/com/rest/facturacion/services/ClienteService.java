package com.rest.facturacion.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rest.facturacion.dao.IClienteRepository;
import com.rest.facturacion.entities.Cliente;
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
	public Cliente findOne(Long id) {
		boolean exists = this.clienteDAO.findById(id).isPresent();
		if(exists) return this.clienteDAO.findById(id).get();
		return null;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.clienteDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse ( "2017-08-25" );
			this.clienteDAO.findByCreatedAt(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}   
		
		return this.clienteDAO.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente fetchClientWithFacturaById(Long id) {
		return this.clienteDAO.fetchClientWithFacturaById(id);
	}

}
