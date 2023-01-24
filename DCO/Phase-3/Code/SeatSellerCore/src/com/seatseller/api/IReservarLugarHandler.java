package com.seatseller.api;

import java.time.LocalDate;
import java.time.LocalTime;

import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;
import com.seatseller.api.wrappers.Combinacao;

/**
 * Handler do Caso de Uso UC6, responsavel por reservar um lugar.
 */
public interface IReservarLugarHandler {

	/**
	 * @param nCli - Nome do cliente
	 * @throws DoesNotExistException - se um cliente com o nome nCli nao existir
	 * @requires cCli != null
	 */
	void indicarCliente(String nCli) throws DoesNotExistException;

	/**
	 * @param date - Data para a reserva
	 * @param time - Hora da reserva
	 * @return lista de objetos contendo informacao sobre lugares disponiveis para
	 *         a data-hora indicadas.
	 * @requires date != null && time != null
	 */
	Iterable<Combinacao> indicarDataHora(LocalDate date, LocalTime time);

	/**
	 * @param grelha - Qual a grelha que se pretende reservar
	 * @param tipoDeLugar - Qual o tipo de lugar que se pretende reservar
	 * @return a identificacao (linha, coluna) do lugar a reservar
	 * @throws DoesNotExistException se uma grelha com a designacao indicada nao
	 *                               existir ou um tipo de lugar com a designacao
	 *                               indicada nao existir
	 */
	String indicarCombinacao(String grelha, String tipoDeLugar) throws DoesNotExistException;

	/**
	 * Indicar que nao se pretende reservar mais lugares para a data-hora indicada
	 * anteriormente
	 */
	void terminarLugares();

	/**
	 * @param num - Numero do cartao de credito
	 * @param ccv - ccv2 do cartao de credito
	 * @param mes - mes da data de validade do cartao de credito
	 * @param ano - mes da data de validade do cartao de credito
	 * @throws InvalidCreditCardException - se o cartao de credito com os
	 *                                      dados indicados nao eh valido
	 */
	double indicarCC(String num, int ccv, int mes, int ano) throws InvalidCreditCardException;

	/**
	 * Conclui a reserva, confirmando
	 * @return O codigo que foi atribuido ah reserva efetuada
	 */
	String confirmarReserva();

}
