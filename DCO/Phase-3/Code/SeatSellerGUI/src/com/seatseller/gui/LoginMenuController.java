package com.seatseller.gui;

import java.util.Optional;
import java.util.logging.Logger;

import com.seatseller.api.ISeatSeller;
import com.seatseller.api.ISessao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginMenuController {
	private static Logger LOGGER = Logger.getLogger(LoginMenuController.class.getName());
	
	private ISeatSeller servicos;
	
	@FXML private TextField username;
	@FXML private PasswordField password;
	
	@FXML protected void login(ActionEvent event) {
		String u = username.getText();
		String p = password.getText();
		
		Optional<ISessao> servicoAutenticado = servicos.autenticar(u, p);
		
		if (!servicoAutenticado.isPresent()) {
			Utils.showError("Error logging in", "Username and password combination do not match.");
			LOGGER.info("Logging in as " + u + " having " + p + " as the password.");
		} else {
			LOGGER.info("Logged in");
			username.setText("");
			password.setText("");
			
			LoggedController.openWindow(servicoAutenticado.get(), "Main Menu", "mainmenu.fxml");
		}
    }

	public void setServicos(ISeatSeller servicos) {
		this.servicos = servicos;
	}
}
