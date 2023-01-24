package com.seatseller.api;


/**
 * Interface implementada pela camada de apresentacao para receber a notificacao
 * de quando um lugar eh reservado numa grelha.
 * 
 * Corresponde ao interface de Observer do padrao Observer.
 */
@FunctionalInterface
public interface INotificacaoReceiver {
	
	/**
	 * Recebe informacao sobre a grelha e o lugar reservados e atua em
	 * conformidade
	 * @param grelha - a descricao da grelha reservada
	 * @param lugar - a descricao do lugar reservado
	 */
	void notificar(String grelha, String lugar);
}
