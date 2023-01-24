package com.seatseller.handlers;

import java.time.LocalTime;

import com.seatseller.api.IRegistarUtilizadorHandler;
import com.seatseller.core.utilizadores.CatalogoUtilizadores;

public class RegistarUtilizadorHandler  implements IRegistarUtilizadorHandler {
	
		public CatalogoUtilizadores catUtilizadores;
		
		public RegistarUtilizadorHandler(CatalogoUtilizadores catUtilizadores) {
			this.catUtilizadores = catUtilizadores;
		}
	
		@Override
		public void registarClienteFinal(String u, String p) {
			catUtilizadores.registarClienteFinal(u, p);
		}

		@Override
		public void registarFuncionario(String u, String p, LocalTime st, LocalTime end) {
			catUtilizadores.registarFuncionario(u, p, st, end);
		}

		@Override
		public void registarAdministrador(String u, String p) {
			catUtilizadores.registarAdmin(u, p);
		}
	}