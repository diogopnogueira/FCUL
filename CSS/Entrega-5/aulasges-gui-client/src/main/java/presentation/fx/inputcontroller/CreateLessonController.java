package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidDurationException;
import facade.exceptions.InvalidLessonNameException;
import facade.exceptions.InvalidSportModalityDurationException;
import facade.exceptions.SportModalityNotFoundException;
import facade.remote.LessonServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.CreateLessonModel;
import presentation.fx.model.SportModalityModel;

public class CreateLessonController extends BaseController {

	private CreateLessonModel createLessonModel;

	private LessonServiceRemote lessonService;

	private Scene mainMenuScene;

	@FXML
	private ComboBox<SportModalityModel> sportModalityComboBox;

	@FXML
	private TextField lessonNameTextField;

	@FXML
	private CheckBox sunday;

	@FXML
	private CheckBox monday;

	@FXML
	private CheckBox tuesday;

	@FXML
	private CheckBox wednesday;

	@FXML
	private CheckBox thursday;

	@FXML
	private CheckBox friday;

	@FXML
	private CheckBox saturday;

	@FXML
	private TextField startHourTextField;

	@FXML
	private TextField startMinuteTextField;

	@FXML
	private TextField durationTextField;

	@FXML
	private Button mainMenuButton;

	public void setLessonService(LessonServiceRemote lessonServiceRemote) {
		this.lessonService = lessonServiceRemote;
	}

	public void setModel(CreateLessonModel createLessonModel) {
		this.createLessonModel = createLessonModel;
		sportModalityComboBox.setItems(createLessonModel.getSportModalities());
		sportModalityComboBox.setValue(createLessonModel.getSelectedSportModality());
		lessonNameTextField.textProperty().bindBidirectional(createLessonModel.lessonNameProperty());
		startHourTextField.textProperty().bindBidirectional(createLessonModel.startHourProperty(),
				new NumberStringConverter());
		startMinuteTextField.textProperty().bindBidirectional(createLessonModel.startMinuteProperty(),
				new NumberStringConverter());
		durationTextField.textProperty().bindBidirectional(createLessonModel.durationProperty(),
				new NumberStringConverter());
	}

	@FXML
	private void initialize() {
		configureCheckBox(sunday);
		configureCheckBox(monday);
		configureCheckBox(tuesday);
		configureCheckBox(wednesday);
		configureCheckBox(thursday);
		configureCheckBox(friday);
		configureCheckBox(saturday);

		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) {
				return change;
			}
			return null;
		};

		startHourTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		startMinuteTextField
				.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
		durationTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
	}

	private void configureCheckBox(CheckBox checkBox) {
		if (checkBox.isSelected()) {
			createLessonModel.getSelectedDaysOfWeek().add(checkBox.getId());
		}

		checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			if (isNowSelected) {
				createLessonModel.getSelectedDaysOfWeek().add(checkBox.getId());
			} else {
				createLessonModel.getSelectedDaysOfWeek().remove(checkBox.getId());
			}

		});

	}

	@FXML
	void createLessonAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				lessonService.addLesson(createLessonModel.getSelectedSportModality().getName(),
						createLessonModel.getLessonName(),
						createLessonModel.getSelectedDaysOfWeek().stream().collect(Collectors.toList())
								.toArray(new String[createLessonModel.getSelectedDaysOfWeek().size()]),
						createLessonModel.getStartHour(), createLessonModel.getStartMinute(),
						createLessonModel.getDuration());
				createLessonModel.clearProperties();
				sportModalityComboBox.getSelectionModel().clearSelection();
				sunday.setSelected(false);
				monday.setSelected(false);
				tuesday.setSelected(false);
				wednesday.setSelected(false);
				thursday.setSelected(false);
				friday.setSelected(false);
				saturday.setSelected(false);
				showInfo(i18nBundle.getString("new.lesson.success"));

			} catch (InvalidLessonNameException e) {
				showError(i18nBundle.getString("new.lesson.error.lessonInvalid") + ": " + e.getMessage());
			} catch (InvalidDurationException | InvalidSportModalityDurationException e) {
				showError(i18nBundle.getString("new.lesson.error.duration") + ": " + e.getMessage());
			} catch (SportModalityNotFoundException e) {
				showError(i18nBundle.getString("new.lesson.error.sportModalityInvalid") + ": " + e.getMessage());
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("new.lesson.error.adding") + ": " + e.getMessage());
			}
		} else {
			showError(i18nBundle.getString("new.lesson.error.validating") + ":\n" + errorMessages);
		}
	}

	private String validateInput() {
		StringBuilder sb = new StringBuilder();
		String lessonName = createLessonModel.getLessonName();
		if (lessonName == null || lessonName.length() == 0)
			sb.append(i18nBundle.getString("new.lesson.invalid.lessonName"));

		if (createLessonModel.getStartHour() >= 60) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.lesson.invalid.startHour"));
		}

		if (createLessonModel.getStartMinute() >= 60) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.lesson.invalid.startMinute"));
		}

		if (createLessonModel.getDuration() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.lesson.invalid.duration"));
		}

		if (createLessonModel.getSelectedSportModality() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.lesson.invalid.sportModality"));
		}

		if (createLessonModel.getSelectedDaysOfWeek() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.lesson.invalid.daysOfWeek"));
		}

		return sb.toString();
	}

	@FXML
	void sportModalitySelected(ActionEvent event) {
		createLessonModel.setSelectedSportModality(sportModalityComboBox.getValue());
	}

	public void setMenuScene(Scene mainMenuScene) {
		this.mainMenuScene = mainMenuScene;
	}

	@FXML
	void navigateToMainMenu(ActionEvent event) {
		Stage mainMenuStage = (Stage) mainMenuButton.getScene().getWindow();
		mainMenuStage.setScene(mainMenuScene);
	}

}
