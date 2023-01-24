package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;

import facade.exceptions.ApplicationException;
import facade.exceptions.FacilityNotFoundException;
import facade.exceptions.InvalidFacilityDateException;
import facade.exceptions.InvalidFacilityParticipantsException;
import facade.exceptions.InvalidFacilitySportModalityException;
import facade.exceptions.InvalidLessonStatusException;
import facade.exceptions.InvalidOccupationDateException;
import facade.exceptions.LessonNotFoundException;
import facade.remote.LessonServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.ActivateLessonModel;
import presentation.fx.model.FacilityModel;

public class ActivateLessonController extends BaseController {

	private ActivateLessonModel activateLessonModel;

	private LessonServiceRemote lessonService;

	private Scene mainMenuScene;

	@FXML
	private ComboBox<FacilityModel> facilityComboBox;

	@FXML
	private TextField lessonNameTextField;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private TextField participantsNumberTextField;

	@FXML
	private Button mainMenuButton;

	public void setLessonService(LessonServiceRemote lessonServiceRemote) {
		this.lessonService = lessonServiceRemote;
	}

	public void setMenuScene(Scene menuScene) {
		this.mainMenuScene = menuScene;

	}

	public void setModel(ActivateLessonModel activateLessonModel) {
		this.activateLessonModel = activateLessonModel;
		facilityComboBox.setItems(activateLessonModel.getFacilities());
		facilityComboBox.setValue(activateLessonModel.getSelectedFacility());
		lessonNameTextField.textProperty().bindBidirectional(activateLessonModel.getLessonNameProperty());
		startDatePicker.valueProperty().bindBidirectional(activateLessonModel.getStartDateProperty());
		endDatePicker.valueProperty().bindBidirectional(activateLessonModel.getEndDateProperty());
		participantsNumberTextField.textProperty()
				.bindBidirectional(activateLessonModel.getParticipantsNumberProperty(), new NumberStringConverter());
	}

	@FXML
	private void initialize() {
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) {
				return change;
			}
			return null;
		};
		startDatePicker.getEditor().setDisable(true);
		endDatePicker.getEditor().setDisable(true);
		participantsNumberTextField
				.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
	}

	@FXML
	void activateLessonAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				lessonService.activateLesson(activateLessonModel.getLessonName(),
						activateLessonModel.getSelectedFacility().getName(), activateLessonModel.getStartDate(),
						activateLessonModel.getEndDate(), activateLessonModel.getParticipantsNumber().intValue());
				activateLessonModel.clearProperties();
				facilityComboBox.getSelectionModel().clearSelection();
				showInfo(i18nBundle.getString("activate.lesson.success"));

			} catch (LessonNotFoundException | InvalidLessonStatusException e) {
				showError(i18nBundle.getString("activate.lesson.error.lessonInvalid") + ": " + e.getMessage());
			} catch (FacilityNotFoundException | InvalidFacilitySportModalityException e) {
				showError(i18nBundle.getString("activate.lesson.error.facilityInvalid") + ": " + e.getMessage());
			} catch (InvalidFacilityDateException | InvalidOccupationDateException e) {
				showError(i18nBundle.getString("activate.lesson.error.dateInvalid") + ": " + e.getMessage());
			} catch (InvalidFacilityParticipantsException e) {
				showError(i18nBundle.getString("activate.lesson.error.participantsInvalid") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("activate.lesson.error.activating") + ": " + e.getMessage());
			}
		} else {
			showError(i18nBundle.getString("activate.lesson.error.validating") + ":\n" + errorMessages);
		}
	}

	private String validateInput() {
		StringBuilder sb = new StringBuilder();
		String lessonName = activateLessonModel.getLessonName();
		if (lessonName == null || lessonName.length() == 0)
			sb.append(i18nBundle.getString("activate.lesson.invalid.lessonName"));

		if (activateLessonModel.getStartDate() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.lesson.invalid.startDate"));
		}

		if (activateLessonModel.getEndDate() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.lesson.invalid.endDate"));
		}

		if (activateLessonModel.getParticipantsNumber() <= 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.lesson.invalid.participantsNumber"));
		}

		if (activateLessonModel.getSelectedFacility() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.lesson.invalid.facility"));
		}

		return sb.toString();
	}

	@FXML
	void facilitySelected(ActionEvent event) {
		activateLessonModel.setSelectedFacility(facilityComboBox.getValue());
	}

	@FXML
	void navigateToMainMenu(ActionEvent event) {
		Stage mainMenuStage = (Stage) mainMenuButton.getScene().getWindow();
		mainMenuStage.setScene(mainMenuScene);
	}

}
