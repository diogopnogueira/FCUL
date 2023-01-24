package com.seatseller.core.utilizadores;


import java.time.LocalTime;

public class Funcionario extends Utilizador {
	
	
	// Hora de início de turnos
	private LocalTime start;
	// Hora de final de turnos
	private LocalTime end;
	
	public Funcionario(String u, String p, LocalTime start, LocalTime end) {
		super(u, p);
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Deve fazser login apenas se a password estiver correta, 
	 * e se a hora actual estiver entre as horas de inicio e final
	 * de turnos deste Funcionario.
	 * 
	 * @see com.seatseller.core.utilizadores.Utilizador#tryLogin(java.lang.String)
	 */
	@Override
	public boolean tryLogin(String pw) {
		LocalTime hora = LocalTime.now();
		return (super.tryLogin(pw) && this.start.isBefore(hora) && this.end.isAfter(hora));		
	}

}
