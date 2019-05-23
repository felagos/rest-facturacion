package com.rest.facturacion.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rest.facturacion.entities.Factura;
import com.rest.facturacion.entities.Producto;
import com.rest.facturacion.services.interfaces.IFacturaService;

@RestController
@RequestMapping("/api/factura")
public class FacturaRestController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IFacturaService facturaService;
	
	@GetMapping(value = "/buscar-productos/{term}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Producto> buscarProductos(@PathVariable("term") String term) {
		List<Producto> productos = this.facturaService.findByName(term);
		return productos;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void crearFactura(@Valid @RequestBody Factura factura) {
		
	}

}
