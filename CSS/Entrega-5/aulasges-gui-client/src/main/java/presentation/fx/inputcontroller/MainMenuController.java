package presentation.fx.inputcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController extends BaseController {

	private Scene createLessonScene;

	private Scene activateLessonScene;

	@FXML
	private Button createLessonButton;

	@FXML
	private Button activateLessonButton;

	public void setCreateLessonScene(Scene createLessonScene) {
		this.createLessonScene = createLessonScene;
	}

	public void setActivateLessonScene(Scene activateLessonScene) {
		this.activateLessonScene = activateLessonScene;
	}

	@FXML
	void navigateCreateLesson(ActionEvent event) {
		Stage mainMenuStage = (Stage) createLessonButton.getScene().getWindow();
		mainMenuStage.setScene(createLessonScene);
	}

	@FXML
	void navigateActivateLesson(ActionEvent event) {
		Stage mainMenuStage = (Stage) activateLessonButton.getScene().getWindow();
		mainMenuStage.setScene(activateLessonScene);
	}

}
