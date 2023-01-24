package com.seatseller.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.seatseller.api.ISessao;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class LoggedController {
	private static Logger LOGGER = Logger.getLogger(LoggedController.class.getName());
	
	protected ISessao servicos;
	
	public void setServicosDisponiveisAutenticados(ISessao iServicosDisponiveisAutenticados) {
		this.servicos = iServicosDisponiveisAutenticados;
	}
	
	public abstract void ready();
	
	
	public static void openLoggedController(LoggedController controller, ISessao serv) {
		controller.setServicosDisponiveisAutenticados(serv);
		controller.ready();
	}
	
	public static <T extends LoggedController> T openWindow(ISessao serv, String name, String file) {
		Stage stage = new Stage();
		stage.setTitle(name);
		Pane myPane = null;
		FXMLLoader fxmlLoader = new FXMLLoader(LoggedController.class.getResource(file)); 
		try {
			myPane = fxmlLoader.load();
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error loading fxml", e);
		}
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		T controller = fxmlLoader.getController();
		LoggedController.openLoggedController(controller, serv);
		stage.show();
		return controller;
	}
}
