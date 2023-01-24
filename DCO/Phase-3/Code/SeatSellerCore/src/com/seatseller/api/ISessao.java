package com.seatseller.api;

/**
 * ISessao disponibiliza os handlers dos casos de uso que necessitam de
 * autenticacao.
 * 
 * Instancias da ISessao devem ser pedidas ao ISeatSeller, de modo a que
 * possam co-existir varias sessoes em simultaneo.
 * 
 * Esta interface disponibiliza metodos para ver o tipo de utilizadores e 
 * devolve os diferentes handlers.
 */
public interface ISessao {
	
	/**
	 * O utilizador autenticado eh cliente final?
	 */
	boolean isClienteFinal();

	/**
	 * O utilizador autenticado eh administrador?
	 */
    boolean isAdministrador();

    /**
	 * O utilizador autenticado eh funcionario?
	 */
    boolean isFuncionario();

	/**
	 * O handler para o caso de uso Criar tipo de lugar
	 * @requires isAdministrador()
	 */
	ICriarTipoDeLugarHandler getCriarTipoDeLugarHandler();
	
	/**
	 * O handler para o caso de uso Criar grelha
	 * @requires isAdministrador()
	 */
	ICriarGrelhaHandler getCriarGrelhaHandler();
	
	/**
	 * O handler para o caso de uso Reservar lugar
	 */
	IReservarLugarHandler getReservarLugarHandler();
	
	/**
	 * O handler para o caso de uso Concluir reserva
	 */
	IConcluirReservaHandler getConcluirReservaHandler();
	
	/**
	 * O handler para o caso de uso Associar grelha
	 * @requires isFuncionario()
	 */
	IAssociarGrelhaHandler getAssociarGrelhaHandler();
	
	/**
	 * O handler para o caso de uso Desassociar grelha
	 * @requires isFuncionario()
	 */
	IDesassociarGrelhaHandler getDesassociarGrelhaHandler();
	
}
