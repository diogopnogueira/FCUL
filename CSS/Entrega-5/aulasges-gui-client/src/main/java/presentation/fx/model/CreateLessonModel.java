package presentation.fx.model;

import facade.exceptions.ApplicationException;
import facade.remote.LessonServiceRemote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateLessonModel {

	private final ObservableList<SportModalityModel> sportModalities;
	private final ObjectProperty<SportModalityModel> selectedSportModality;

	private final StringProperty lessonName;

	private final ObservableList<String> selectedDaysOfWeek;

	private final IntegerProperty startHour;
	private final IntegerProperty startMinute;
	private final IntegerProperty duration;

	public CreateLessonModel(LessonServiceRemote lessonService) {
		sportModalities = FXCollections.observableArrayList();
		lessonName = new SimpleStringProperty();
		selectedDaysOfWeek = FXCollections.observableArrayList();
		startHour = new SimpleIntegerProperty();
		startMinute = new SimpleIntegerProperty();
		duration = new SimpleIntegerProperty();
		try {
			lessonService.newLesson()
					.forEach(sm -> sportModalities.add(new SportModalityModel(sm.getId(), sm.getName())));
		} catch (ApplicationException e) {

		}
		selectedSportModality = new SimpleObjectProperty<>(null);
	}

	public ObjectProperty<SportModalityModel> selectedSportModalityProperty() {
		return selectedSportModality;
	}

	public final SportModalityModel getSelectedSportModality() {
		return selectedSportModality.get();
	}

	public final void setSelectedSportModality(SportModalityModel sportModality) {
		selectedSportModality.set(sportModality);
	}

	public ObservableList<SportModalityModel> getSportModalities() {
		return sportModalities;
	}

	public String getLessonName() {
		return lessonName.get();
	}

	public StringProperty lessonNameProperty() {
		return lessonName;
	}

	public ObservableList<String> getSelectedDaysOfWeek() {
		return selectedDaysOfWeek;
	}

	public int getStartHour() {
		return startHour.get();
	}

	public IntegerProperty startHourProperty() {
		return startHour;
	}

	public int getStartMinute() {
		return startMinute.get();
	}

	public IntegerProperty startMinuteProperty() {
		return startMinute;
	}

	public int getDuration() {
		return duration.get();
	}

	public IntegerProperty durationProperty() {
		return duration;
	}

	public void clearProperties() {
		lessonName.set("");
		startHour.set(0);
		startMinute.set(0);
		duration.set(0);
		selectedSportModality.set(null);
	}
}
