package com.rest.facturacion.dto;

import java.util.Arrays;

import lombok.Data;

@Data
public class FacturaDTO {
	
	private String descripcion;
	private String observacion;
	private Long[] itemsId;
	private Integer[] cantidad;
	
	@Override
	public String toString() {
		return "FacturaDTO [descripcion=" + descripcion + ", observacion=" + observacion + ", itemsId="
				+ Arrays.toString(itemsId) + ", cantidad=" + Arrays.toString(cantidad) + "]";
	}
	
}
