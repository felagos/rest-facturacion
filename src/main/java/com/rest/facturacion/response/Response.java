package com.rest.facturacion.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
	
	public static <T> ResponseEntity<T> createResponse(T body, HttpStatus status){
		return new ResponseEntity<T>(body, status);
	}

}
