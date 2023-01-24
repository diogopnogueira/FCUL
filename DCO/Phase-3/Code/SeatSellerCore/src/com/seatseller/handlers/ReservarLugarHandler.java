package com.seatseller.handlers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import com.seatseller.api.IReservarLugarHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;
import com.seatseller.api.wrappers.Combinacao;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.reservas.CatalogoReservas;
import com.seatseller.core.utilizadores.CatalogoUtilizadores;
import com.seatseller.core.utilizadores.Utilizador;

public class ReservarLugarHandler implements IReservarLugarHandler {

	CatalogoUtilizadores catUtilizadores;
	Optional<Utilizador> cli;
	
	ReservarLugarHandler(Utilizador utilizador, CatalogoGrelhas catG, CatalogoTiposDeLugar catT, CatalogoReservas catR, CatalogoUtilizadores catU){
		
	}
	
	@Override
	public void indicarCliente(String username) throws DoesNotExistException {
		cli = catUtilizadores.getCliente(username);
		if(cli.isPresent()){
			cli.get();
		}	
		else
			throw new DoesNotExistException("Erro nao existe este tipo de lugar");
	}


	@Override
	public Iterable<Combinacao> indicarDataHora(LocalDate date, LocalTime time) {
		
		return new ArrayList<>();
	}


	@Override
	public String indicarCombinacao(String grelha, String tipoDeLugar) throws DoesNotExistException {
		// TODO
		return "";
	}


	@Override
	public void terminarLugares() {
		// TODO
	}


	@Override
	public double indicarCC(String numero, int ccv, int mes, int ano) throws InvalidCreditCardException {
		// TODO
		return 0.0;
	}


	@Override
	public String confirmarReserva() {
		// TODO
		return "";
	}

}
