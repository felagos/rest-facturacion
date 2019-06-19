package com.rest.facturacion.services;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rest.facturacion.dao.IFacturaRepository;
import com.rest.facturacion.dao.IProductoRepository;
import com.rest.facturacion.dto.FacturaDTO;
import com.rest.facturacion.entities.Factura;
import com.rest.facturacion.entities.Producto;
import com.rest.facturacion.exceptions.NotFoundException;
import com.rest.facturacion.services.interfaces.IFacturaService;

@Service
public class FacturaService implements IFacturaService {
	
	@Autowired
	private IProductoRepository productoDAO;
	
	@Autowired
	private IFacturaRepository facturaDAO;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByName(String term) {
		return this.productoDAO.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void guardarFactura(Factura factura) {
		this.facturaDAO.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductById(Long id) {
		boolean exists = this.productoDAO.findById(id).isPresent();
		if(exists) return this.productoDAO.findById(id).get();
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		boolean exists = this.facturaDAO.findById(id).isPresent();
		return exists ? this.facturaDAO.findById(id).get() : null;
	}

	@Override
	@Transactional
	public void borrarFactura(Long id) {
		this.facturaDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public FacturaDTO getFacturaById(Long id) throws NotFoundException {
		Factura factura = this.facturaDAO.fetchFacturaById(id);
		if(factura == null) throw new NotFoundException("NOT FOUND");
		
		factura.getCliente().setFacturas(new ArrayList<>());
		FacturaDTO dto = modelMapper.map(factura, FacturaDTO.class);
		
		return dto;
	}

}
