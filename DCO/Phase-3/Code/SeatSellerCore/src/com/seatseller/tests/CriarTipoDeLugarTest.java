package com.seatseller.tests;

import org.junit.Test;

public class CriarTipoDeLugarTest extends LoggedTest {

	@Test
	public void testAdmin() {
		admin.getCriarTipoDeLugarHandler().criarTipoDeLugar("a", "a", 1.0, true);
	}

}
