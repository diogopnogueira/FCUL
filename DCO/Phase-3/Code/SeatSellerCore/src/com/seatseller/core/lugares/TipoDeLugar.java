package com.seatseller.core.lugares;

public class TipoDeLugar {
	private String desig;
	private String descricao;
	private double preco;
	
	public TipoDeLugar(String d, String desc, double preco) {
		this.desig = d;
		this.descricao = desc;
		this.preco = preco;
	}

	public String getDesig() {
		return desig;
	}

	public void setDesig(String desig) {
		this.desig = desig;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getPreco() {
		return preco;
	}
	
	@Override
	public String toString() {
		return desig;
	}
}
