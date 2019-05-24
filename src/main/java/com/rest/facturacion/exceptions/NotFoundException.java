package com.rest.facturacion.exceptions;

public class NotFoundException extends Exception {
	
	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String message, Throwable e) {
		super(message, e);
	}

}
