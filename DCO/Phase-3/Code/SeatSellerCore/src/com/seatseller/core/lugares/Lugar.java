package com.seatseller.core.lugares;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class Lugar {

	private int preco;
	private Optional<TipoDeLugar> tipoLugar;
	private int fila;
	private int coluna;
	private boolean disponibilidade;
	
	public Lugar(int prec, Optional<TipoDeLugar> tipoLugar, int fi, int col) {
		this.preco = prec;
		this.setTipoLugar(tipoLugar);
		this.fila = fi;
		this.coluna = col;
		this.disponibilidade = true;
	}
	
	public int getPreco() {
		return preco;
	}

	public void setPreco(int preco) {
		this.preco = preco;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public Optional<TipoDeLugar> getTipoLugar() {
		return tipoLugar;
	}

	public void setTipoLugar(Optional<TipoDeLugar> tipoLugar) {
		this.tipoLugar = tipoLugar;
	}

	public boolean disponivel(LocalDate data, LocalTime hora){
		
	}
}
