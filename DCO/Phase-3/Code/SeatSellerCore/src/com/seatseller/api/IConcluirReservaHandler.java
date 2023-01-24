package com.seatseller.api;

import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;

/**
 * Handler do caso de uso UC7 - Concluir Reserva
 */
public interface IConcluirReservaHandler {
	
	/**
	 * Verifica o valor que falta pagar de uma dada reserva
	 * @param codRes - Codigo da reserva
	 * @return O valor que falta pagar da reserva em questao
	 * @throws DoesNotExistException - se nao existir nenhuma reserva com o codigo
	 *                                 codRes
	 */
	double confirmarValorEmFalta(String codRes) throws DoesNotExistException;
	
	/**
	 * Conclui o pagamento da reserva em questao
	 * @param num - Numero do cartao de credito
	 * @param ccv - ccv2 do cartao de credito
	 * @param mes - mes da data de validade do cartao de credito
	 * @param ano - mes da data de validade do cartao de credito
	 * @throws InvalidCreditCardException - se o cartao de credito com os
	 *                                      dados indicados nao eh valido
	 */
	void indicarCC(String num, int ccv, int mes, int ano) throws InvalidCreditCardException;
}
