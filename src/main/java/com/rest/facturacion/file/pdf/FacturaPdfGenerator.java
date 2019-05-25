package com.rest.facturacion.file.pdf;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.rest.facturacion.dto.FacturaDTO;
import com.rest.facturacion.dto.ItemFacturaDTO;

public class FacturaPdfGenerator  {
	
	public static ByteArrayInputStream reportFactura(FacturaDTO factura) {
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
        	
        	PdfPTable tabla = new PdfPTable(1);
    		tabla.setSpacingAfter(20);

    		PdfPCell cell = null;

    		cell = new PdfPCell(new Phrase("Datos del cliente"));
    		cell.setBackgroundColor(new Color(184, 218, 255));
    		cell.setPadding(8f);
    		tabla.addCell(cell);

    		tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
    		tabla.addCell(factura.getCliente().getEmail());

    		PdfPTable tabla2 = new PdfPTable(1);
    		tabla2.setSpacingAfter(20);

    		cell = new PdfPCell(new Phrase("Datos de la factura"));
    		cell.setBackgroundColor(new Color(195, 230, 203));
    		cell.setPadding(8f);

    		tabla2.addCell(cell);
    		tabla2.addCell("Folio" + ": " + factura.getId());
    		tabla2.addCell("Descripción" + ": " + factura.getDescripcion());
    		tabla2.addCell("Fecha" + ": " + factura.getCreatedAt());

    		PdfPTable tabla3 = new PdfPTable(4);
    		tabla3.setWidths(new float[] { 3.5f, 1, 1, 1 });
    		tabla3.addCell("Nombre");
    		tabla3.addCell("Precio");
    		tabla3.addCell("Cantidad");
    		tabla3.addCell("Total");

    		for (ItemFacturaDTO item : factura.getItemFacturas()) {
    			tabla3.addCell(item.getProducto().getNombre());
    			tabla3.addCell(item.getProducto().getPrecio().toString());

    			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
    			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
    			tabla3.addCell(cell);
    			tabla3.addCell(item.calcularImporte().toString());
    		}

    		cell = new PdfPCell(new Phrase("Total: "));
    		cell.setColspan(3);
    		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    		tabla3.addCell(cell);
    		tabla3.addCell(factura.getTotal().toString());
    		
    		PdfWriter.getInstance(document, out);
            document.open();
            
            document.add(tabla);
            document.add(tabla);
    		document.add(tabla2);
            
            document.close();
        	
        } catch (DocumentException e) {
        	e.printStackTrace();
        }
        
        return new ByteArrayInputStream(out.toByteArray());
	}

	/*@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Factura factura = (Factura) model.get("factura");

		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);

		PdfPCell cell = null;

		cell = new PdfPCell(new Phrase("Datos del cliente"));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		tabla.addCell(cell);

		tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla.addCell(factura.getCliente().getEmail());

		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);

		cell = new PdfPCell(new Phrase("Datos de la factura"));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);

		tabla2.addCell(cell);
		tabla2.addCell("Folio" + ": " + factura.getId());
		tabla2.addCell("Descripción" + ": " + factura.getDescripcion());
		tabla2.addCell("Fecha" + ": " + factura.getCreatedAt());

		document.add(tabla);
		document.add(tabla2);

		PdfPTable tabla3 = new PdfPTable(4);
		tabla3.setWidths(new float[] { 3.5f, 1, 1, 1 });
		tabla3.addCell("Nombre");
		tabla3.addCell("Precio");
		tabla3.addCell("Cantidad");
		tabla3.addCell("Total");

		for (ItemFactura item : factura.getItemFacturas()) {
			tabla3.addCell(item.getProducto().getNombre());
			tabla3.addCell(item.getProducto().getPrecio().toString());

			cell = new PdfPCell(new Phrase(item.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			tabla3.addCell(item.calcularImporte().toString());
		}

		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(factura.getTotal().toString());
		
		document.addTitle("PDF");

		document.add(tabla3);

	}*/

}
