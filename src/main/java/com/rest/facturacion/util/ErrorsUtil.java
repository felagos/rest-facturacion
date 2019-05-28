package com.rest.facturacion.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.FieldError;

public class ErrorsUtil {
	
	public static List<String> getErrors(List<FieldError> errors) {
		return errors.stream().map(error -> error.getField() + " " + error.getDefaultMessage()).collect(Collectors.toList());
	}

}
