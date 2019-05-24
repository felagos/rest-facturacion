package com.rest.facturacion.services.interfaces;

import java.util.List;

import com.rest.facturacion.dto.FacturaDTO;
import com.rest.facturacion.entities.Factura;
import com.rest.facturacion.entities.Producto;
import com.rest.facturacion.exceptions.NotFoundException;

public interface IFacturaService {
	
	public List<Producto> findByName(String term);
	
	public void guardarFactura(Factura factura);
	
	public Producto findProductById(Long id);
	
	public Factura findFacturaById(Long id);
		
	public void borrarFactura(Long id);
	
	public FacturaDTO getFacturaById(Long id) throws NotFoundException;

}
