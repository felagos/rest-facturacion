package com.rest.facturacion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.rest.facturacion.Response;
import com.rest.facturacion.entities.Cliente;
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
	public List<Cliente> listar() {
		return this.clienteService.findAll();
	}
	
	/*@GetMapping("/listar")
	public List<Cliente> listarPage(Model model, @RequestParam(value =  "page", defaultValue = "0") int page) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = this.clienteService.findAll(pageRequest);
		return clientes.getContent();
	}*/
	
	@GetMapping("/borrar/{id}")
	public ResponseEntity<Response> borrar(@PathVariable("id") Long id, RedirectAttributes flash) {
		Cliente cliente = this.clienteService.findOne(id);
		if(cliente == null) {
			return ResponseEntity.badRequest().body(new Response(HttpStatus.NOT_FOUND, ""));
		}
		
		String foto = cliente.getFoto();
		this.fileService.deleteFile(foto);
		
		this.clienteService.delete(id);
		
		return ResponseEntity.ok().body(new Response(HttpStatus.OK, ""));

	}
	
	@PostMapping("/guardar")
	public void guardar(Cliente cliente) {		
		this.clienteService.save(cliente);
	}

}
