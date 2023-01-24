package com.seatseller.core.pagamentos;

public class Pagamento {
	
	boolean cativar;
	double valor;
	
	public Pagamento(boolean cativar, double valor){
		this.cativar = cativar;
		this.valor = valor;
	}
}
