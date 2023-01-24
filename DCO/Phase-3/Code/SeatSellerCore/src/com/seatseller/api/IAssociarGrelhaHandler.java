package com.seatseller.api;

/**
 * Handler do Caso de Uso UC9 - Associar grelha
 * Este handler soh estarah acessivel caso o utilizador autenticado seja 
 * funcionario
 */
public interface IAssociarGrelhaHandler {

	/**
	 * Associa uma dada grelha ao funcionario autenticado
	 * @param desig - Designacao da grelha a associar
	 * @param c - O receiver das notificacoes que serao enviadas ao
	 *            funcionario sempre que sao feitas reservas de lugares      
	 * @requires desig != null && c != null
	 * @ensures Se uma grelha com a designacao desig existir, ela fica
	 *          associada ao funcionario autenticado
	 */
	void associarGrelha(String desig, INotificacaoReceiver c);

}
