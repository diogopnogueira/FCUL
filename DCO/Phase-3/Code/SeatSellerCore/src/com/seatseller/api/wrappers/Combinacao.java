package com.seatseller.api.wrappers;

/**
 * Classe wrapper que "embrulha" 4 tipos de informacao sobre a disponibildade
 * de lugares em grelhas
 */
public class Combinacao {
	private String grelha;
	private String tipoDeLugar;
	private double preco;
	private int disponibilidade;
	
    /**
     * 
     * @param grelha - a designacao da grelha
     * @param tipo - o tipo de lugar disponivel
     * @param p - o preco desse tipo de lugar
     * @requires grelha != null && tipo != null
     * @ensures getGrelha().equals(grelha) && getTipoDeLugar().equals(tipo) &&
     *          getPreco()= preco && getDisponibilidade() = 1
     */
	public Combinacao(String grelha, String tipo, double p) {
		this.grelha = grelha;
		this.tipoDeLugar = tipo;
		this.preco = p;
		this.disponibilidade = 1;
	}

	public String getGrelha() {
		return grelha;
	}

	public String getTipoDeLugar() {
		return tipoDeLugar;
	}

	public double getPreco() {
		return preco;
	}

	public int getDisponibilidade() {
		return disponibilidade;
	}
	
	/**
	 * @ensures getDisponibilidade() == \old(getDisponibilidade()) + 1
	 */
	public void aumentaDisponibilidade() {
		disponibilidade++;
	}
	
	public String toString() {
		return this.getGrelha() + " | " + this.getTipoDeLugar() + " | " + 
	           this.getPreco() + " € | " + this.getDisponibilidade() + " livres";
	}
}
