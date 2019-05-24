package com.rest.facturacion.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class FacturaDTO {
	private Long id;
	private String descripcion;
	private String observacion;
	private Date createdAt;
	private ClienteDTO cliente;
	private List<ItemFacturaDTO> itemFacturas;
}
