package com.seatseller.handlers;

import java.util.Optional;

import com.seatseller.api.ICriarGrelhaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.NonUniqueException;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.TipoDeLugar;

public class CriarGrelhaHandler implements ICriarGrelhaHandler {
	
	private CatalogoTiposDeLugar catTipos;
	private CatalogoGrelhas catGrelhas;
	private Grelha grelha;
	private Optional<TipoDeLugar> tp;
	private Optional<TipoDeLugar> tlp;
	
	public CriarGrelhaHandler(CatalogoGrelhas catG, CatalogoTiposDeLugar catT) {
		this.catTipos = new CatalogoTiposDeLugar();
		this.catGrelhas = new CatalogoGrelhas();
	}

	@Override
	public void iniciarGrelha(String desig, double ind) throws NonUniqueException {
		boolean existe = catGrelhas.existeGrelha(desig);
		if(!existe)
			grelha = new Grelha(desig, ind);
		else
			throw new NonUniqueException("Erro nao existe este tipo de lugar");
	}

	@Override
	public Optional<String> indicarDimensao(int i, int j) {
		tlp = catTipos.getPadrao();
		if(tlp.isPresent()){
			grelha.criaLugares(i, j, tlp);
		}
		else{
			grelha.criaLugares(i, j, null);
		}
		return Optional.ofNullable(tlp.get().getDesig());
		}

	@Override
	public void indicarTipoPadrao(String desig) throws DoesNotExistException {
		//catTipos.getTipo(desig).isPresent()
		tp = catTipos.getTipo(desig);
		if(tp.isPresent())
			grelha.defineTipoLugarPadrao(tp);
		else
			throw new DoesNotExistException("Erro nao existe este tipo de lugar");
		
	}

	@Override
	public void indicarTipoLugar(int i, int j, String desig) throws DoesNotExistException {
		tp = catTipos.getTipo(desig);
		if(tp.isPresent() && grelha.coordenadasValidas(i,j)){
			grelha.defineTipoLugar(i,j,tp);
		}
		else{
			throw new DoesNotExistException("Erro coordenadas invalidas");
		}
	}

	@Override
	public void terminar() {
		catGrelhas.acrescentaGrelha(grelha);
	}

}
