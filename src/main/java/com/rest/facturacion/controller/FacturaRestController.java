package com.rest.facturacion.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.rest.facturacion.dto.FacturaDTO;
import com.rest.facturacion.entities.Cliente;
import com.rest.facturacion.entities.Factura;
import com.rest.facturacion.entities.Producto;
import com.rest.facturacion.exceptions.NotFoundException;
import com.rest.facturacion.response.Response;
import com.rest.facturacion.services.interfaces.IClientService;
import com.rest.facturacion.services.interfaces.IFacturaService;

@RestController
@RequestMapping("/api/factura")
public class FacturaRestController {

	@Autowired
	private IFacturaService facturaService;

	@Autowired
	private IClientService clienteService;

	@GetMapping(value = "/buscar-productos/{term}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Producto> buscarProductos(@PathVariable("term") String term) {
		List<Producto> productos = this.facturaService.findByName(term);
		return productos;
	}

	@PostMapping("/crear/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> crearFactura(@Valid @RequestBody Factura factura,
			@PathVariable(name = "id", required = true) Long idClient) {
		try {
			Cliente cliente = this.clienteService.findOne(idClient);
			factura.setCliente(cliente);

			for (int i = 0; i < factura.getItemFacturas().size(); i++) {
				long id = factura.getItemFacturas().get(i).getId();
				Producto producto = this.facturaService.findProductById(id);

				factura.getItemFacturas().get(i).setId(null);
				factura.getItemFacturas().get(i).setProducto(producto);
			}

			this.facturaService.guardarFactura(factura);

			return Response.createResponse("created", HttpStatus.CREATED);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return Response.createResponse(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/detalle/{id}")
	public ResponseEntity<?> detalleFactura(@PathVariable(name = "id", required = true) Long id) {
		try {
			FacturaDTO factura = this.facturaService.getFacturaById(id);
			
			return Response.createResponse(factura, HttpStatus.OK);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return Response.createResponse(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

}
