package com.seatseller.gui;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Utils {
	public static void showError(String title, String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public static void closeWindow(Scene scene) {
		Window window = scene.getWindow();
        if (window instanceof Stage){
            ((Stage) window).close();
        }
	}

	public static void showInfo(String title, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.showAndWait();
	}
}
