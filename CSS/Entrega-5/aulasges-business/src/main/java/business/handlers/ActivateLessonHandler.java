package business.handlers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.catalogs.FacilityCatalog;
import business.catalogs.LessonCatalog;
import business.facility.Facility;
import business.facility.FacilityValidator;
import business.lesson.Lesson;
import business.lesson.LessonValidator;
import business.occupation.Occupation;
import business.occupation.OccupationValidator;
import facade.dto.ActiveLessonDTO;
import facade.dto.FacilityDTO;
import facade.exceptions.ApplicationException;

@Stateless
public class ActivateLessonHandler {

	@EJB
	private FacilityCatalog facilityCatalog;

	@EJB
	private LessonCatalog lessonCatalog;

	public Collection<FacilityDTO> newActivation() throws ApplicationException {
		try {
			List<Facility> facilities = facilityCatalog.getAllFacilities();
			return getFacilities(facilities);
		} catch (Exception e) {
			throw new ApplicationException("Error fetching facilities", e);
		}
	}

	public ActiveLessonDTO activateLesson(String lessonName, String facilityName, LocalDate startDate,
			LocalDate endDate, int maxParticipants) throws ApplicationException {

		Lesson lesson = lessonCatalog.getLessonByName(lessonName);
		Facility facility = facilityCatalog.getFacilityByName(facilityName);

		LessonValidator.lessonExists(lesson);
		LessonValidator.lessonIsAlreadyActive(lesson);
		FacilityValidator.facilityExists(facility);
		FacilityValidator.facilityAllowsSportModality(facility, lesson);
		FacilityValidator.facilityIsAvailable(facility, lesson);
		FacilityValidator.validateMaxParticipantsOnFacility(facility, maxParticipants);
		OccupationValidator.validateOccupationDate(startDate, endDate);
		try {
			facility.addOccupation(new Occupation(startDate, endDate, lesson));
			lesson.activate(facility, maxParticipants);
			Lesson newActiveLesson = lessonCatalog.persistLesson(lesson);
			return new ActiveLessonDTO(newActiveLesson.getId(), newActiveLesson.getName(),
					newActiveLesson.getDaysOfWeek().stream().map(Object::toString).collect(Collectors.joining(",")),
					newActiveLesson.getStartHour().toString(), newActiveLesson.getEndHour().toString(),
					newActiveLesson.getFacility().getName(), newActiveLesson.getVacancies());
		} catch (Exception e) {
			throw new ApplicationException("Error activating new lesson", e);
		}
	}

	private List<FacilityDTO> getFacilities(List<Facility> entityList) {
		List<FacilityDTO> result = new ArrayList<>();
		for (Facility facility : entityList)
			result.add(new FacilityDTO(facility.getId(), facility.getName()));
		return result;
	}

}