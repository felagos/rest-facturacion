package com.rest.facturacion.services.interfaces;

import org.springframework.core.io.InputStreamResource;
import com.rest.facturacion.dto.FacturaDTO;
import com.rest.facturacion.exceptions.FileGeneratorException;

public interface IFileGenerator {
	
	public InputStreamResource createPDFFactura(FacturaDTO factura);
	public InputStreamResource createExcelFactura(FacturaDTO factura) throws FileGeneratorException;

}
