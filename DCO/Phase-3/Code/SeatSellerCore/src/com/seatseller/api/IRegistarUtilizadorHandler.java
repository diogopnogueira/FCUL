package com.seatseller.api;

import java.time.LocalTime;

/**
 * Handler do Caso de Uso UC1A e B, responsavel por registar um Utilizador
 */
public interface IRegistarUtilizadorHandler {
	
	/**
	 * Regista um utilizador Cliente final
	 * @param u - Username do utilizador
	 * @param p - Password do utilizador
	 * @requires u != null && p != null
	 */
	void registarClienteFinal(String u, String p);

	/**
	 * Regista um utilizador Funcionario. Esse funcionario soh poderah
	 * fazer login durante o seu turno.
	 * @param u - Username do utilizador
	 * @param p - Password do utilizador
	 * @param st - Hora de entrada do funcionario
	 * @param st - Hora de saida do funcionario
	 * @requires u != null && p != null && st != null && end != null
	 */
    void registarFuncionario(String u, String p, LocalTime st, LocalTime end);

    /**
	 * Regista um utilizador Administrador
	 * @param u - Username do utilizador
	 * @param p - Password do utilizador
	 * @requires u != null && p != null
	 */
    void registarAdministrador(String u, String p);
}
