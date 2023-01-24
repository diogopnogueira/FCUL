package com.seatseller.core.reservas;

import java.time.LocalDate;
import java.time.LocalTime;

public class LinhaReserva {
	
	private LocalDate data;
	private LocalTime hora;

	public LinhaReserva(LocalDate data, LocalTime hora) {
		this.data = data;
		this.hora = hora;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	
	
	
	
	
	
}
