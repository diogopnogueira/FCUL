package com.seatseller.core.lugares;

import java.security.KeyStore.Entry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CatalogoGrelhas {

	private Map<String,Grelha> grelhas = new HashMap<>();
	private ArrayList<Combinacao> combinacoes = new ArrayList<>();

	public boolean existeGrelha(String desig) {
		if(grelhas.containsKey(desig))
			return true;
		else		
			return false;
	}

	public void acrescentaGrelha(Grelha grelha) {
		grelhas.put(grelha.getDesig(), grelha);
	}
	
	public Iterable<Combinacao> getCombinacoes(LocalDate data, LocalTime hora){
		return combinacoes;
		
		
	}
}
