package com.seatseller.core.utilizadores;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CatalogoUtilizadores {
	private Map<String, Utilizador> utilizadores = new HashMap<>();
	
	public void registarAdmin(String u, String p) {
		Utilizador utilizador = new Administrador(u, p);
		utilizadores.put(u, utilizador);
	}
	
	public void registarFuncionario(String u, String p, LocalTime st, LocalTime end) {
		Utilizador utilizador = new Funcionario(u, p, st, end);
		utilizadores.put(u, utilizador);
	}
	
	public void registarClienteFinal(String u, String p) {
		Utilizador utilizador = new ClienteFinal(u, p);
		utilizadores.put(u, utilizador);
	}

	public boolean autenticar(String u, String p) {
		return utilizadores.containsKey(u) && utilizadores.get(u).tryLogin(p);
	}

	public Utilizador getUtilizador(String u) {
		return utilizadores.get(u);
	}

	public Optional<Utilizador> getCliente(String username) {
		return Optional.ofNullable(utilizadores.get(username)).filter(u -> u instanceof ClienteFinal);
	}
	
}
