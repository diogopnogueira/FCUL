package com.seatseller.handlers;

import com.seatseller.api.ICriarTipoDeLugarHandler;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;

public class CriarTipoDeLugarHandler implements ICriarTipoDeLugarHandler {
	
	public CatalogoTiposDeLugar cataTipos;
	
	public CriarTipoDeLugarHandler(CatalogoTiposDeLugar catTipos) {
		this.cataTipos = catTipos;
	}
	
	@Override
	public void criarTipoDeLugar(String desig, String desc, double preco, boolean padrao) {
		cataTipos.criarTipoDeLugar(desig, desc, preco, padrao);
	}
}
