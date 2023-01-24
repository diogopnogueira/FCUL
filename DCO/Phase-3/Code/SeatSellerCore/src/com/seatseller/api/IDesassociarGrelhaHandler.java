package com.seatseller.api;

/**
 * Handler do Caso de Uso UC10 - Desassociar grelha
 * Este handler soh estarah acessivel caso o utilizador autenticado seja 
 * funcionario
 */
public interface IDesassociarGrelhaHandler {

	/**
	 * Desassocia uma dada grelha do funcionario autenticado
	 * @param desig - Designacao da grelha a desassociar
	 * @param c - O receiver das notificacoes que foram enviadas ao
	 *            funcionario sempre que foram feitas reservas de lugares      
	 * @requires desig != null && c != null
	 * @ensures Se uma grelha com a designacao desig existir, ela fica
	 *          desassociada do funcionario autenticado
	 */
	void desassociarGrelha(String desig, INotificacaoReceiver c);

}
