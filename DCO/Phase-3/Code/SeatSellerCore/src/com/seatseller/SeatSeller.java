package com.seatseller;

import java.util.Optional;

import com.seatseller.api.IRegistarUtilizadorHandler;
import com.seatseller.api.ISeatSeller;
import com.seatseller.api.ISessao;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.utilizadores.CatalogoUtilizadores;
import com.seatseller.handlers.RegistarUtilizadorHandler;
import com.seatseller.handlers.Sessao;

public class SeatSeller implements ISeatSeller {

	private CatalogoUtilizadores catUtilizadores = new CatalogoUtilizadores();
	private CatalogoTiposDeLugar catTipos = new CatalogoTiposDeLugar();

	@Override
	public IRegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return new RegistarUtilizadorHandler(catUtilizadores);
	}

	@Override
	public Optional<ISessao> autenticar(String u, String p) {
		boolean autenticado = catUtilizadores.autenticar(u, p);
		
		if (!autenticado) return Optional.empty();
		return Optional.of(new Sessao(u, catUtilizadores, catTipos)); // TODO
	}

}
