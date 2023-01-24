package com.seatseller.tests;

import java.time.LocalTime;

import org.junit.After;
import org.junit.Before;

import com.seatseller.SeatSeller;
import com.seatseller.api.ISeatSeller;
import com.seatseller.api.ISessao;

public abstract class LoggedTest {

	protected ISeatSeller app;
	protected ISessao admin;
	protected ISessao func;
	protected ISessao cli;
	
	@Before
	public void setUp() throws Exception {
		app = new SeatSeller();
		app.getRegistarUtilizadorHandler().registarAdministrador("a", "a");
		admin = app.autenticar("a", "a").get();
		
		app.getRegistarUtilizadorHandler().registarFuncionario("b", "b", LocalTime.parse("00:00"), LocalTime.parse("23:59"));//13:00-16:00
		func = app.autenticar("b", "b").get();
		
		app.getRegistarUtilizadorHandler().registarClienteFinal("c", "c");
		cli = app.autenticar("c", "c").get();
		
		admin.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar Normal", "Lugar típico na sala de cinema", 7.00, false);
		admin.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar VIP", "Lugar com cadeira reclinavel", 10.00, false);
		admin.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar Love Seat", "Lugar sem divisória de um dos lados", 8.00, false);
		admin.getCriarTipoDeLugarHandler().criarTipoDeLugar("Lugar Acessível", "Lugar adaptado a cadeiras de rodas", 2.00, false);
		
	}

	@After
	public void tearDown() throws Exception {
		app = null;
		admin = null;
		func = null;
		cli = null;
	}

}
