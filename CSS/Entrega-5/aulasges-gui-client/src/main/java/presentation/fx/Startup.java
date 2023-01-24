package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.remote.LessonServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.ActivateLessonController;
import presentation.fx.inputcontroller.CreateLessonController;
import presentation.fx.inputcontroller.MainMenuController;
import presentation.fx.model.ActivateLessonModel;
import presentation.fx.model.CreateLessonModel;

public class Startup extends Application {

	private static LessonServiceRemote lessonService;

	@Override
	public void start(Stage stage) throws IOException {

		// This line to resolve keys against Bundle.properties
		// ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new
		// Locale("en", "UK"));
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));

		FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), i18nBundle);
		Parent root = mainLoader.load();
		Scene menuScene = new Scene(root, 736, 342);
		MainMenuController mainMenuController = mainLoader.getController();
		mainMenuController.setI18NBundle(i18nBundle);

		FXMLLoader createLessonLoader = new FXMLLoader(getClass().getResource("/fxml/createLesson.fxml"), i18nBundle);
		Parent createLessonPane = createLessonLoader.load();
		Scene createLessonScene = new Scene(createLessonPane, 736, 342);
		CreateLessonController createLessonController = createLessonLoader.getController();
		CreateLessonModel createLessonModel = new CreateLessonModel(lessonService);
		createLessonController.setLessonService(lessonService);
		createLessonController.setI18NBundle(i18nBundle);
		createLessonController.setModel(createLessonModel);

		FXMLLoader activateLessonLoader = new FXMLLoader(getClass().getResource("/fxml/activateLesson.fxml"),
				i18nBundle);
		Parent activateLessonPane = activateLessonLoader.load();
		Scene activateLessonScene = new Scene(activateLessonPane, 736, 342);
		ActivateLessonController activateLessonController = activateLessonLoader.getController();
		ActivateLessonModel activateLessonModel = new ActivateLessonModel(lessonService);
		activateLessonController.setLessonService(lessonService);
		activateLessonController.setI18NBundle(i18nBundle);
		activateLessonController.setModel(activateLessonModel);

		mainMenuController.setCreateLessonScene(createLessonScene);
		mainMenuController.setActivateLessonScene(activateLessonScene);
		createLessonController.setMenuScene(menuScene);
		activateLessonController.setMenuScene(menuScene);

		stage.setTitle(i18nBundle.getString("application.title"));
		stage.setScene(menuScene);
		stage.show();
	}

	public static void startGUI(LessonServiceRemote lessonService) {
		Startup.lessonService = lessonService;
		launch();
	}
}
