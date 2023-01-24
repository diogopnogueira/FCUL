package com.seatseller.handlers;

import com.seatseller.api.IConcluirReservaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;

public class ConcluirReservaHandler implements IConcluirReservaHandler {


	@Override
	public double confirmarValorEmFalta(String codigo) throws DoesNotExistException {
		// TODO
		return 0.0;
	}

	@Override
	public void indicarCC(String numero, int ccv, int mes, int ano) throws InvalidCreditCardException {
		// TODO
	}

}
