package com.seatseller.core.reservas;

public class ReservaFactory {
	// Singleton a la Alcides
	
		// Passo 1: Instancia estatica
		private static ReservaFactory INSTANCE = new ReservaFactory();
		private static int codigoReservas = 0;
		
		// Passo 2: getter estatico da instancia
		public static ReservaFactory getInstance() {
			return INSTANCE;
		}

		// Passo 3: Construtor privado
		private ReservaFactory() {
		}

		private int novoCodigo(){
			return codigoReservas++;
		}

		public Reserva getProximaReserva(){
			return new Reserva(novoCodigo());
		}
		
}
