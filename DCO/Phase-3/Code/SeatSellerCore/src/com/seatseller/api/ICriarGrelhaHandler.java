package com.seatseller.api;

import java.util.Optional;

import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.NonUniqueException;


/**
 * Handler do Caso de Uso UC4 - Criar grelha
 * Este handler soh estarah acessivel caso o utilizador autenticado seja 
 * administrador
 */
public interface ICriarGrelhaHandler {
	
	/**
	 * Inicia o caso de uso
	 * @param desig - Designacao da grelha a criar
	 * @param ind - Indice de preco
	 * @throws NonUniqueException - se jah existe uma grelha com a designacao 
	 *                              desig
	 */
	void iniciarGrelha(String desig, double ind) throws NonUniqueException;
	
	/**
	 * @param alt - Numero de "linhas" da grelha
	 * @param larg - Numero de "colunas" da grelha
	 * @return o tipo de lugar padrão definido no sistema, se existir
	 */
	Optional<String> indicarDimensao(int alt, int larg);
	
	/**
	 * @param tip - Tipo de lugar para servir de padrao para esta grelha
	 * @throws DoesNotExistException - se nao existe um tipo de lugar com a
	 *                                 designacao tip
	 */
	void indicarTipoPadrao(String tip) throws DoesNotExistException;
	
	/**
	 * @param i - "linha" do lugar a alterar
	 * @param j - "coluna" do lugar a alterar
	 * @param tip - tipo de lugar a atribuir ao lugar indicado
	 * @throws DoesNotExistException - se nao existe um tipo de lugar com a
	 *                                 designacao tip
	 */
	void indicarTipoLugar(int i, int j, String tip) throws DoesNotExistException;
	
	/**
	 * Concluir a criacao da grelha
	 */
	void terminar();
}
