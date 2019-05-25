package com.rest.facturacion.exceptions;

public class FileGeneratorException extends Exception {
	
	public FileGeneratorException(String message) {
		super(message);
	}
	
	public FileGeneratorException(String message, Throwable e) {
		super(message, e);
	}
}
