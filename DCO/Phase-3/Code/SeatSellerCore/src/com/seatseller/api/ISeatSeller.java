package com.seatseller.api;

import java.util.Optional;

/**
 * ISeatSeller eh o entrypoint do programa, que disponibiliza os casos de uso
 * que nao necessitam de autenticacao, nomeadamente o de autenticar.
 */
public interface ISeatSeller {
	IRegistarUtilizadorHandler getRegistarUtilizadorHandler();
	
	/**
	 * Tenta autenticar um utilizador com um username e password. No caso da 
	 * autenticacao estar correcta, devolve uma sessão, a partir da qual 
	 * se podem obter os handlers dos casos de uso que necessitam da autenticacao
	 * @param u - Username do utilizador
	 * @param p - Password do utilizador
	 * @requires u != null && p != null
	 * @return Um objeto do tipo ISessao se um utilizador de nome u e password p
	 *         existir
	 */
	Optional<ISessao> autenticar(String u, String p);
}
