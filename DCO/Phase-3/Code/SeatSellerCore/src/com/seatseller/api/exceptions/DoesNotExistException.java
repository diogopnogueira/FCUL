package com.seatseller.api.exceptions;

/**
 * Excecao levantada quando algum elemento nao existe. 
 * 
 * Pode ser subclassed especificando o que nao existe.
 */
public class DoesNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public DoesNotExistException(String str) {
		super(str);
	}

}
