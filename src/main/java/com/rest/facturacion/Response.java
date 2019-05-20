package com.rest.facturacion;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Response {
	
	private HttpStatus code;
	private String message;
	
	public Response(HttpStatus code) {
		this.message = "";
		this.code = code;
	}
	
	public Response(HttpStatus code, String message) {
		this.code = code;
		this.message = message;
	}
	

}
