package com.rest.facturacion.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import com.rest.facturacion.dto.FacturaDTO;
import com.rest.facturacion.exceptions.FileGeneratorException;
import com.rest.facturacion.file.excel.FacturaExcelGenerator;
import com.rest.facturacion.file.pdf.FacturaPdfGenerator;
import com.rest.facturacion.services.interfaces.IFileGenerator;

@Service
public class FileGenerator implements IFileGenerator {

	@Override
	public InputStreamResource createPDFFactura(FacturaDTO factura) {
		return new InputStreamResource(FacturaPdfGenerator.reportFactura(factura));
	}

	@Override
	public InputStreamResource createExcelFactura(FacturaDTO factura) throws FileGeneratorException {
		return new InputStreamResource(FacturaExcelGenerator.generateExcel(factura));
	}


}
