package com.seatseller.gui;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.seatseller.api.ICriarGrelhaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class CriarGrelhaController extends LoggedController {
	private static Logger LOGGER = Logger.getLogger(CriarGrelhaController.class.getName());
	
	@FXML private TitledPane p1;
	@FXML private TitledPane p2;
	@FXML private TitledPane p3;
	@FXML private TitledPane p4;
	
	@FXML private TextField nome;
	@FXML private TextField indice;
	private ICriarGrelhaHandler handler;
	
	private void openPane(TitledPane p) {
		p.setCollapsible(true);
		p.setExpanded(true);
		p.setCollapsible(false);
	}
	
	private void closePane(TitledPane p) {
		p.setCollapsible(true);
		p.setExpanded(false);
		p.setCollapsible(false);
	}
	
	@Override
	public void ready() {
		handler = servicos.getCriarGrelhaHandler();
	}
	
	@FXML protected void iniciaGrelha(ActionEvent event) {
		double ind = Double.parseDouble(indice.getText());
		try {
			handler.iniciarGrelha(nome.getText(), ind);
			LOGGER.log(Level.INFO, "Grelha " + nome.getText() + " criada");
			
			closePane(p1);
			openPane(p2);
			
		} catch (Exception e) {
			Utils.showError("Erro ao criar grelha", "Nome da Grelha já existe.");
			LOGGER.log(Level.SEVERE, "Grelha " + nome.getText() + " já existe", e);
		}
		
	}
	
	@FXML private TextField altura;
	@FXML private TextField largura;
	
	@FXML protected void defineDimensoes(ActionEvent event) {
		int alt = Integer.parseInt(altura.getText());
		int lar = Integer.parseInt(largura.getText());
		
		Optional<String> tipoPadrao = handler.indicarDimensao(alt, lar);
		
		if (tipoPadrao.isPresent()) {
			closePane(p2);
			openPane(p4);
			setUpP4();
		} else {
			closePane(p2);
			openPane(p3);
			setUpP3();
			
		}
	}
	
	private void setUpP3() {
		this.tipoPadrao.setItems(FXCollections.observableArrayList("Lugar Normal", "Lugar VIP", "Lugar Love Seat", "Lugar Acessível"));
	}

	private void setUpP4() {
		this.tipoLugar.setItems(FXCollections.observableArrayList("Lugar Normal", "Lugar VIP", "Lugar Love Seat", "Lugar Acessível"));
	}

	@FXML private ChoiceBox<String> tipoPadrao;
	
	@FXML protected void defineLugarPadrao(ActionEvent event) {
		try {
			handler.indicarTipoPadrao(tipoPadrao.getValue());
			closePane(p3);
			openPane(p4);
			setUpP4();
		} catch (DoesNotExistException e) {
			Utils.showError("Erro ao definir tipo de lugar padrão", "Nome do tipo de lugar não existe.");
			LOGGER.log(Level.SEVERE, "Nome do tipo de lugar não existe.", e);
		}
	}
	
	
	@FXML private TextField linha;
	@FXML private TextField coluna;
	@FXML private ChoiceBox<String> tipoLugar;

	@FXML protected void define(ActionEvent event) {
		try {
			int i = Integer.parseInt(linha.getText());
			int j = Integer.parseInt(coluna.getText());
			String tipo = tipoPadrao.getValue();
			handler.indicarTipoLugar(i, j, tipo);
		} catch (DoesNotExistException e) {
			Utils.showError("Coordenadas não existem.", "As coordenadas do lugar estão erradas");
			LOGGER.log(Level.SEVERE, "Coordenadas erradas.", e);
		}
	}
	
	@FXML protected void termina(ActionEvent event) {
		handler.terminar();
		LoggedController.openWindow(servicos, "Main Menu", "mainmenu.fxml");
		Utils.closeWindow(tipoLugar.getScene());
	}
}
