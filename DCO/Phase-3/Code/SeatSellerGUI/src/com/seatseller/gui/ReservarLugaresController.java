package com.seatseller.gui;

import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.seatseller.api.IReservarLugarHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.wrappers.Combinacao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class ReservarLugaresController extends LoggedController {
	private static Logger LOGGER = Logger.getLogger(ReservarLugaresController.class.getName());
	
	private IReservarLugarHandler handler;
	
	@FXML private TitledPane p1;
	@FXML private TitledPane p2;
	@FXML private TitledPane p3;
	@FXML private TitledPane p4;
	@FXML private TitledPane p5;
	
	@FXML private TextField cliente;
	
	
	
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
		handler = servicos.getReservarLugarHandler();
		if (servicos.isClienteFinal()) {
			closePane(p1);
			openPane(p2);
		}
	}
	
	@FXML protected void indicaCliente(ActionEvent event) {
		
		try {
			handler.indicarCliente(cliente.getText());
			closePane(p1);
			openPane(p2);
		} catch (DoesNotExistException e) {
			Utils.showError("Username inválido.", "Esse cliente não existe");
			LOGGER.log(Level.SEVERE, "Cliente não existe", e);
		}

	}
	
	@FXML private DatePicker data;
	@FXML private TextField hora;
	
	private Iterable<Combinacao> combs;
	
	@FXML protected void indicaDataHora (ActionEvent event) {
		
		LocalTime horaMin = LocalTime.of(Integer.parseInt(hora.getText()), 00);
		combs = handler.indicarDataHora(data.getValue(), horaMin);
		hora.setText("");
		data.setValue(null);
		closePane(p2);
		openPane(p3);
		ObservableList<Combinacao> l = FXCollections.observableArrayList();
		for (Combinacao c : combs) {
			l.add(c);
		}
		this.combinacoes.setItems(l);
	}

	@FXML private ChoiceBox<Combinacao> combinacoes;
	@FXML private ListView<String> lugaresEscolhidos;
	ObservableList<String> lugaresEscolhidosList = FXCollections.observableArrayList();
	
	@FXML protected void escolheLugar(ActionEvent event) {
		lugaresEscolhidos.setItems(lugaresEscolhidosList);
		Combinacao escolhida = combinacoes.getValue();
		try {
			String lugar = handler.indicarCombinacao(escolhida.getGrelha(), escolhida.getTipoDeLugar());
			System.out.println("Lugar: " + lugar);
			lugaresEscolhidosList.add(lugar);
			lugaresEscolhidos.refresh();
		} catch (DoesNotExistException e) {
			Utils.showError("Grelha ou Tipo de Lugar inválico.", "Combinacação inválida");
			LOGGER.log(Level.SEVERE, "Combinacação inválida", e);
		}
	}
	
	@FXML protected void terminaLugares(ActionEvent event) {
		handler.terminarLugares();
		closePane(p3);
		openPane(p4);
	}
	
	@FXML protected void repeteLugares(ActionEvent event) {
		handler.terminarLugares();
		lugaresEscolhidosList.clear();
		lugaresEscolhidos.refresh();
		closePane(p3);
		openPane(p2);
	}
	
	
	@FXML private TextField cc;
	@FXML private TextField ccv2;
	@FXML private TextField mm;
	@FXML private TextField aa;

	@FXML protected void validar(ActionEvent event) {
		try {
			int iccv2 = Integer.parseInt(ccv2.getText());
			int imm = Integer.parseInt(mm.getText());
			int iaa = Integer.parseInt(aa.getText());
			
			double valor = handler.indicarCC(cc.getText(), iccv2, imm, 2000 + iaa);
			
			closePane(p4);
			openPane(p5);
			
			valorAPagar.setText(valor + "€");
			
		} catch (Exception e) {
			Utils.showError("Cartão de Crédito Errado.", "Validação do cartão de crédito falhou");
			LOGGER.log(Level.SEVERE, "CC errado", e);
		}
		
	}
	
	@FXML private Label valorAPagar;

	
	@FXML protected void confirmar(ActionEvent event) {
		String codigo = handler.confirmarReserva();
		Utils.showInfo("Reserva efectuada.", "Código: " + codigo);
		LOGGER.log(Level.INFO, "Reserva concluída: " + codigo);
		LoggedController.openWindow(servicos, "Main Menu", "mainmenu.fxml");
		Utils.closeWindow(valorAPagar.getScene());
	}
}
