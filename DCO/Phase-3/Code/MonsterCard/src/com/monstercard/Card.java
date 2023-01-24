package com.monstercard;

import java.time.LocalDate;

public class Card {
	private String month;
	private String year;
	
	public Card(String number, String ccv, String month, String year) {
		this.month = month;
		this.year = year;
	}
	
	public boolean validate() {
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		int cy = LocalDate.now().getYear();
		int cm = LocalDate.now().getMonth().getValue();
		
		return (y > cy || (y == cy && m >= cm));
	}
}
