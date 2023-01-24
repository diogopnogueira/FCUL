package com.seatseller.core.lugares;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Grelha {
	
	private String desig;
	private double ind;
	private int altura;
	private int largura;
	private ArrayList<Lugar> listaLugares;
	private ArrayList<Combinacao> listaCombinacoes;

	public Grelha(String desig, double ind) {
		this.desig = desig;
		this.ind = ind;
		this.listaLugares = new ArrayList<>();
	}
	
	public String getDesig() {
		return desig;
	}

	public void setDesig(String desig) {
		this.desig = desig;
	}

	public double getInd() {
		return ind;
	}

	public void setInd(double ind) {
		this.ind = ind;
	}
	
	public List<Lugar> getLista() {
		return listaLugares;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}
	
	
	public boolean contains(String desig2) {
		return (this.desig.equals(desig2));
	}
	

	public void criaLugares(int altura, int largura, Optional<TipoDeLugar> padrao) {
		this.altura = altura;
		this.largura = largura;
	
		for (int a = 1; a <= altura; a++) {
			for (int l = 1; l <= largura; l++) {
				listaLugares.add(new Lugar(a*l, padrao, a, l));//Preço deve estar errado
			}	
		}	
	}
	
	public void defineTipoLugarPadrao(Optional<TipoDeLugar> tp){
		for (Lugar lugar : listaLugares) {
			lugar.setTipoLugar(tp);
		}
	}
	
	public boolean coordenadasValidas(int altura, int largura){
		if(altura < 0 || largura < 0 || altura > this.altura || largura > this.largura){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void defineTipoLugar(int a, int l, Optional<TipoDeLugar> tp){
		int index = ((a-1)*largura +l) -1;
		listaLugares.get(index).setTipoLugar(tp);
	}

	public ArrayList<Combinacao> getListaCombinacoes() {
		return listaCombinacoes;
	}
	
	
	
	
	
}
