package com.rest.facturacion.file.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.rest.facturacion.dto.FacturaDTO;
import com.rest.facturacion.dto.ItemFacturaDTO;
import com.rest.facturacion.exceptions.FileGeneratorException;

public class FacturaExcelGenerator {

	public static ByteArrayInputStream generateExcel(FacturaDTO factura) throws FileGeneratorException {
		try (Workbook workbook = new XSSFWorkbook()) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			Sheet sheet = workbook.createSheet("Factura");

			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);

			cell.setCellValue("Datos del cliente");
			sheet.createRow(1).createCell(0)
					.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
			sheet.createRow(2).createCell(0).setCellValue(factura.getCliente().getEmail());

			sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
			sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId());
			sheet.createRow(6).createCell(0).setCellValue("Descripción: " + factura.getDescripcion());
			sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreatedAt());

			CellStyle theaderStyle = workbook.createCellStyle();
			theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
			theaderStyle.setBorderTop(BorderStyle.MEDIUM);
			theaderStyle.setBorderRight(BorderStyle.MEDIUM);
			theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
			theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
			theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			CellStyle tbodyStyle = workbook.createCellStyle();
			tbodyStyle.setBorderBottom(BorderStyle.THIN);
			tbodyStyle.setBorderTop(BorderStyle.THIN);
			tbodyStyle.setBorderRight(BorderStyle.THIN);
			tbodyStyle.setBorderLeft(BorderStyle.THIN);

			Row header = sheet.createRow(9);

			header.createCell(0).setCellValue("Nombre");
			header.createCell(1).setCellValue("Precio");
			header.createCell(2).setCellValue("Cantidad");
			header.createCell(3).setCellValue("Total");

			header.getCell(0).setCellStyle(theaderStyle);
			header.getCell(1).setCellStyle(theaderStyle);
			header.getCell(2).setCellStyle(theaderStyle);
			header.getCell(3).setCellStyle(theaderStyle);

			int rowNum = 10;
			for (ItemFacturaDTO item : factura.getItemFacturas()) {
				Row rowItem = sheet.createRow(rowNum++);

				rowItem.createCell(0).setCellValue(item.getProducto().getNombre());
				rowItem.getCell(0).setCellStyle(tbodyStyle);

				rowItem.createCell(1).setCellValue(item.getProducto().getPrecio());
				rowItem.getCell(1).setCellStyle(tbodyStyle);

				rowItem.createCell(2).setCellValue(item.getCantidad());
				rowItem.getCell(2).setCellStyle(tbodyStyle);

				rowItem.createCell(3).setCellValue(item.calcularImporte());
				rowItem.getCell(3).setCellStyle(tbodyStyle);
			}

			Row rowTotal = sheet.createRow(rowNum);
			rowTotal.createCell(2).setCellValue("Total importe");
			rowTotal.getCell(2).setCellStyle(tbodyStyle);

			rowTotal.createCell(3).setCellValue(factura.getTotal());
			rowTotal.getCell(3).setCellStyle(tbodyStyle);

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileGeneratorException(e.getMessage(), e);
		}

	}

}
