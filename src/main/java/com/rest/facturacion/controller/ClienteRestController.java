package com.rest.facturacion.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.rest.facturacion.entities.Cliente;
import com.rest.facturacion.exceptions.NotFoundException;
import com.rest.facturacion.response.Response;
import com.rest.facturacion.services.interfaces.IClientService;
import com.rest.facturacion.services.interfaces.IFileUploadService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	private IClientService clienteService;

	@Autowired
	private IFileUploadService fileService;

	@GetMapping(value = "/listar")
	public ResponseEntity<List<Cliente>> listar() {
		List<Cliente> clientes =  this.clienteService.findAll();
		return Response.createResponse(clientes, HttpStatus.OK);
	}

	/*@GetMapping("/listar/{page}")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Cliente> listarPage(@PathVariable(value = "page") int page) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = this.clienteService.findAll(pageRequest);
		return clientes.getContent();
	}*/

	@DeleteMapping("/borrar/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<String> borrar(@PathVariable("id") Long id, RedirectAttributes flash) {
		try {
			this.clienteService.delete(id);
			return Response.createResponse("created", HttpStatus.OK);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return Response.createResponse(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/guardar")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void guardar(@Valid @RequestBody Cliente cliente) {
		this.clienteService.save(cliente);
	}
	
	@GetMapping("/findOne/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> buscarCliente(@PathVariable(name = "id") Long id) {
		try {
			Cliente cliente = this.clienteService.findOne(id);
			return Response.createResponse(cliente, HttpStatus.OK);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return Response.createResponse(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}
