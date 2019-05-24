package com.rest.facturacion.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ClienteDTO {
	private Long id;
	private String nombre;
	private String apellido;
	private String email;
	private Date createdAt;
	private String foto;
	private List<FacturaDTO> facturas = new ArrayList<>();
}
