package com.rest.facturacion.exceptions;

public class ClienteNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteNotFoundException(String message) {
		super(message);
	}
	
	public ClienteNotFoundException(String message, Throwable e) {
		super(message, e);
	}

}
