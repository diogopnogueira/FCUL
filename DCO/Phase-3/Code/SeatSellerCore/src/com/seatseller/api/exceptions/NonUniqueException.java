package com.seatseller.api.exceptions;

/**
 * Excecao levantada quando se tenta criar algo com um nome que jah existe. 
 * 
 * Pode ser subclassed especificando o que jah existe.
 */
public class NonUniqueException extends Exception {

	private static final long serialVersionUID = 1L;

	public NonUniqueException(String str) {
		super(str);
	}

}
