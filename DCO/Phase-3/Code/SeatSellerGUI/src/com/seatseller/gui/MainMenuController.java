package com.seatseller.gui;

import java.util.logging.Logger;

import com.seatseller.api.INotificacaoReceiver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController extends LoggedController {

	private static Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());

	@FXML private Button criarGrelhaButton;
	
	@FXML protected void criarGrelha(ActionEvent event) {
		LoggedController.openWindow(servicos, "CriarGrelha", "criar_grelha.fxml");
		
		Utils.closeWindow(criarGrelhaButton.getScene());
	}
	
	@FXML protected void reservarLugares(ActionEvent event) {
		LoggedController.openWindow(servicos, "Reservar Lugares", "reservar_lugares.fxml");
		Utils.closeWindow(criarGrelhaButton.getScene());
	}
	
	@FXML protected void concluirReserva(ActionEvent event) {
		LoggedController.openWindow(servicos, "Concluir Pagamento", "concluir_pagamento.fxml");
		Utils.closeWindow(criarGrelhaButton.getScene());
	}


	public void ready() {
		if (servicos.isClienteFinal()) {
			criarGrelhaButton.setDisable(true);
		}
		
		if (servicos.isFuncionario()) {
			INotificacaoReceiver c = new INotificacaoReceiver() {

				@Override
				public void notificar(String grelha, String lugar) {
					Utils.showInfo("Notificação de reserva", lugar);
				}
				
			};
			servicos.getAssociarGrelhaHandler().associarGrelha("Sala 1", c);
			servicos.getAssociarGrelhaHandler().associarGrelha("Sala VIP", c);
		}
		
		LOGGER.info("Main menu ready");
	}

}