package com.seatseller.core.lugares;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CatalogoTiposDeLugar {
	private Map<String, TipoDeLugar> tipos = new HashMap<>();
	private Optional<TipoDeLugar> tipoPadrao = Optional.empty();
	
	public void criarTipoDeLugar(String desig, String desc, double preco, boolean padrao) {
		TipoDeLugar t = new TipoDeLugar(desig, desc, preco);
		tipos.put(desig, t);
		if (padrao) tipoPadrao = Optional.ofNullable(t);
	}

	public Optional<TipoDeLugar> getPadrao() {
		return tipoPadrao;
	}

	public Optional<TipoDeLugar> getTipo(String desig) {
		return Optional.ofNullable(tipos.get(desig));
	}
}
