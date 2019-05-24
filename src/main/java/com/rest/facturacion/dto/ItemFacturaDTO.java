package com.rest.facturacion.dto;

import lombok.Data;

@Data
public class ItemFacturaDTO {
	private Long id;
	private Integer cantidad;
	private ProductoDTO producto;
}
