package com.jpa.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rest.facturacion.entities.Cliente;
import com.rest.facturacion.services.interfaces.IClientService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {
	
	@Autowired
	private IClientService clienteService;
	
	@GetMapping("/listar")
	public List<Cliente> listar() {
		return this.clienteService.findAll();
	}

}
