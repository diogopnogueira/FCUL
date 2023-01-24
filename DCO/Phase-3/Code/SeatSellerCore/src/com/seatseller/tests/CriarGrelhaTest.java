package com.seatseller.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Test;

import com.seatseller.api.ICriarGrelhaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.NonUniqueException;

public class CriarGrelhaTest extends LoggedTest {
	@Test
	public void testAdmin() {
		ICriarGrelhaHandler cgh = admin.getCriarGrelhaHandler();
		try {
			cgh.iniciarGrelha("Sala 1", 1.0);
		} catch (NonUniqueException e) {
			fail("Should not raise exception");
		}
		
		Optional<String> tipoDeLugarPadrao = cgh.indicarDimensao(10,10);
		
		if (!tipoDeLugarPadrao.isPresent()) {
			try {
				cgh.indicarTipoPadrao("Lugar Normal");
				for(int i=0; i<10; i++) {
					cgh.indicarTipoLugar(5, i, "Lugar Acessível");
				}
			} catch (DoesNotExistException e) {
				fail("Should not raise exception");
			}
		}
		cgh.terminar();
	}
	
	@Test
	public void testDuplicateName() {
		ICriarGrelhaHandler cgh = admin.getCriarGrelhaHandler();
		try {
			cgh.iniciarGrelha("Sala 1", 1.0);
		} catch (NonUniqueException e) {
			fail("Should not raise exception");
		}
		
		try {
			cgh.iniciarGrelha("Sala 1", 1.0);
			fail("Should raise exception");
		} catch (NonUniqueException e) {
			assertTrue(true);
		}
	}
}
