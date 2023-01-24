package presentation.fx.model;

import java.time.LocalDate;

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

public class ActivateLessonModel {

	private final ObservableList<FacilityModel> facilities;
	private final ObjectProperty<FacilityModel> selectedFacility;
	private final StringProperty lessonName;
	private final ObjectProperty<LocalDate> startDate;
	private final ObjectProperty<LocalDate> endDate;
	private final IntegerProperty participantsNumber;

	public ActivateLessonModel(LessonServiceRemote lessonService) {
		facilities = FXCollections.observableArrayList();
		lessonName = new SimpleStringProperty();
		startDate = new SimpleObjectProperty(null);
		endDate = new SimpleObjectProperty(null);
		participantsNumber = new SimpleIntegerProperty();
		try {
			lessonService.newActivation().forEach(f -> facilities.add(new FacilityModel(f.getId(), f.getName())));
		} catch (ApplicationException e) {

		}
		selectedFacility = new SimpleObjectProperty<>(null);
	}

	public ObjectProperty<FacilityModel> getSelectedFacilityProperty() {
		return selectedFacility;
	}

	public final FacilityModel getSelectedFacility() {
		return selectedFacility.get();
	}

	public final void setSelectedFacility(FacilityModel facility) {
		selectedFacility.set(facility);
	}

	public ObservableList<FacilityModel> getFacilities() {
		return facilities;
	}

	public StringProperty getLessonNameProperty() {
		return lessonName;
	}

	public ObjectProperty<LocalDate> getStartDateProperty() {
		return startDate;
	}

	public ObjectProperty<LocalDate> getEndDateProperty() {
		return endDate;
	}

	public IntegerProperty getParticipantsNumberProperty() {
		return participantsNumber;
	}

	public String getLessonName() {
		return lessonName.get();
	}

	public LocalDate getStartDate() {
		return startDate.get();
	}

	public LocalDate getEndDate() {
		return endDate.get();
	}

	public Integer getParticipantsNumber() {
		return participantsNumber.get();
	}

	public void clearProperties() {
		lessonName.set("");
		startDate.set(null);
		endDate.set(null);
		participantsNumber.set(0);
		selectedFacility.set(null);
	}

}
