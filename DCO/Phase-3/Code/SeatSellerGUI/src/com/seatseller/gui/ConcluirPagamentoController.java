package com.seatseller.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.seatseller.api.IConcluirReservaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class ConcluirPagamentoController extends LoggedController {
	private static Logger LOGGER = Logger.getLogger(ConcluirPagamentoController.class.getName());
	
	private IConcluirReservaHandler handler;
	
	@FXML private TitledPane p1;
	@FXML private TitledPane p2;
	
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
		handler = servicos.getConcluirReservaHandler();
	}
	
	@FXML private TextField codigo;
	
	@FXML protected void indicarCodigo(ActionEvent event) {
		try {
			double valor = handler.confirmarValorEmFalta(codigo.getText());
			valorAPagar.setText(valor + "€");
			closePane(p1);
			openPane(p2);
		} catch (DoesNotExistException e) {
			Utils.showError("Reserva inválida.", "Código de Reserva Inválido");
			LOGGER.log(Level.SEVERE, "Codigo errado: " + codigo.getText(), e);
		}
	}
	
	
	@FXML private Label valorAPagar;
	@FXML private TextField cc;
	@FXML private TextField ccv2;
	@FXML private TextField mm;
	@FXML private TextField aa;

	@FXML protected void validar(ActionEvent event) {
		try {
			int iccv2 = Integer.parseInt(ccv2.getText());
			int imm = Integer.parseInt(mm.getText());
			int iaa = Integer.parseInt(aa.getText());
			
			handler.indicarCC(cc.getText(), iccv2, imm, 2000 + iaa);
			Utils.showInfo("Pagamento", "Pagamento concluído");
			LoggedController.openWindow(servicos, "Main Menu", "mainmenu.fxml");
			Utils.closeWindow(valorAPagar.getScene());
			
			
		} catch (Exception e) {
			Utils.showError("Cartão de Crédito Errado.", "Validação do cartão de crédito falhou");
			LOGGER.log(Level.SEVERE, "CC errado", e);
		}
		
	}
}
