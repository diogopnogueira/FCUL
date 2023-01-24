package com.seatseller.core.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.seatseller.core.utilizadores.ClienteFinal;


public class Reserva {
	private ClienteFinal cliente;
	private int codigo;
	private ArrayList<LinhaReserva> listaReservas; 


	public Reserva(int cod){
		this.codigo = cod;
		this.listaReservas = new ArrayList<>();
	}

	public ClienteFinal getCliente() {
		return cliente;
	}


	public void setCliente(ClienteFinal cliente) {
		this.cliente = cliente;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public ArrayList<LinhaReserva> getListaReservas() {
		return listaReservas;
	}
	
	public void novaLinha(LocalDate data, LocalTime hora){
		listaReservas.add(new LinhaReserva(data,hora));
	}
	
}