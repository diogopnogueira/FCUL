package com.seatseller.api.exceptions;

/**
 * Excecao levantada quando um cartao de credito eh invalido. 
 */
public class InvalidCreditCardException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCreditCardException(String str) {
		super(str);
	}

}
