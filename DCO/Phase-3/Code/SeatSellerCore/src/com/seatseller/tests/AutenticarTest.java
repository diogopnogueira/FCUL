package com.seatseller.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.seatseller.SeatSeller;
import com.seatseller.api.ISeatSeller;

public class AutenticarTest {

	ISeatSeller app;
	
	@Before
	public void setUp() throws Exception {
		app = new SeatSeller();
	}

	@After
	public void tearDown() throws Exception {
		app = null;
	}

	@Test
	public void test() {
		app.getRegistarUtilizadorHandler().registarAdministrador("a", "a");
		app.getRegistarUtilizadorHandler().registarFuncionario("b", "b", LocalTime.parse("00:00"), LocalTime.parse("23:59"));
		app.getRegistarUtilizadorHandler().registarClienteFinal("c", "c");
		
		assertTrue(app.autenticar("a", "a").isPresent());
		assertTrue(app.autenticar("b", "b").isPresent());
		assertTrue(app.autenticar("c", "c").isPresent());
		assertFalse(app.autenticar("d", "d").isPresent());
	}

}
