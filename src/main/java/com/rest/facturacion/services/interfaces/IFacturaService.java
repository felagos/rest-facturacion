package com.rest.facturacion.services.interfaces;

import java.util.List;
import com.rest.facturacion.entities.Factura;
import com.rest.facturacion.entities.Producto;

public interface IFacturaService {
	
	public List<Producto> findByName(String term);
	
	public void guardarFactura(Factura factura);
	
	public Producto findProductById(Long id);
	
	public Factura findFacturaById(Long id);
		
	public void borrarFactura(Long id);
	
	public Factura getFacturaById(Long id);

}
