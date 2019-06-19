package com.rest.facturacion.exceptions;

public class FileGeneratorException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public FileGeneratorException(String message) {
		super(message);
	}
	
	public FileGeneratorException(String message, Throwable e) {
		super(message, e);
	}
}
