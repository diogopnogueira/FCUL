package com.seatseller.handlers;

import com.seatseller.Configuration;
import com.seatseller.api.IAssociarGrelhaHandler;
import com.seatseller.api.IConcluirReservaHandler;
import com.seatseller.api.ICriarGrelhaHandler;
import com.seatseller.api.ICriarTipoDeLugarHandler;
import com.seatseller.api.IDesassociarGrelhaHandler;
import com.seatseller.api.IReservarLugarHandler;
import com.seatseller.api.ISessao;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.reservas.CatalogoReservas;
import com.seatseller.core.utilizadores.Administrador;
import com.seatseller.core.utilizadores.CatalogoUtilizadores;
import com.seatseller.core.utilizadores.ClienteFinal;
import com.seatseller.core.utilizadores.Funcionario;
import com.seatseller.core.utilizadores.Utilizador;

public class Sessao implements ISessao {

	private Utilizador utilizador;	
	private CatalogoGrelhas catG;
	private CatalogoTiposDeLugar catT;
	private CatalogoReservas catR;
	private CatalogoUtilizadores catU;
	//private ISistemaCC sisCC;
	private static Configuration config; 

	public Sessao(String u, CatalogoUtilizadores catUtilizadores, CatalogoTiposDeLugar catTipos) { 
		this.utilizador = catUtilizadores.getUtilizador(u);
		config = Configuration.getInstance();
		this.catT = catTipos;
	}

	@Override
	public ICriarTipoDeLugarHandler getCriarTipoDeLugarHandler() {
		return new CriarTipoDeLugarHandler(catT);
	}

	@Override
	public ICriarGrelhaHandler getCriarGrelhaHandler() {
		return new CriarGrelhaHandler(catG, catT);
	}
	
	public IReservarLugarHandler getReservarLugarHandler() {
		return new ReservarLugarHandler();//utilizador, catG, catT, catR, catU, sisCC
	}

	@Override
	public IConcluirReservaHandler getConcluirReservaHandler() {
		return new ConcluirReservaHandler();//utilizador, catR, sisCC
	}

	@Override
	public boolean isClienteFinal() {
		return utilizador instanceof ClienteFinal;
	}

	@Override
	public boolean isAdministrador() {
		return utilizador instanceof Administrador;
	}

	@Override
	public boolean isFuncionario() {
		return utilizador instanceof Funcionario;
	}
	
	@Override
	public IAssociarGrelhaHandler getAssociarGrelhaHandler() {
		return new AssociarGrelhaHandler();//utilizador, catG
	}
	
	@Override
	public IDesassociarGrelhaHandler getDesassociarGrelhaHandler() {
		return new DesassociarGrelhaHandler();//utilizador, catG
	}

	

}
