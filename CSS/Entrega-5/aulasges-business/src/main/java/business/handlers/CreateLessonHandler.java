package business.handlers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.catalogs.LessonCatalog;
import business.catalogs.SportModalityCatalog;
import business.lesson.Lesson;
import business.lesson.LessonValidator;
import business.sportModality.SportModality;
import business.sportModality.SportModalityValidator;
import facade.dto.LessonDTO;
import facade.dto.SportModalityDTO;
import facade.exceptions.ApplicationException;

@Stateless
public class CreateLessonHandler {

	@EJB
	private SportModalityCatalog sportsModalityCatalog;

	@EJB
	private LessonCatalog lessonCatalog;

	public Collection<SportModalityDTO> newLesson() throws ApplicationException {
		try {
			List<SportModality> entityList = sportsModalityCatalog.getAllSportModalities();
			return getSportModalities(entityList);
		} catch (Exception e) {
			throw new ApplicationException("Error fetching sport modalities", e);
		}
	}

	public LessonDTO addLesson(String sportModalityName, String lessonName, String[] daysOfWeekString, int startHour,
			int startMinute, int duration) throws ApplicationException {

		LessonValidator.validateLessonName(lessonName);
		// Lesson lesson = lessonCatalog.getLessonByName(lessonName);
		// LessonValidator.lessonNameAlreadyExists(lesson);
		LessonValidator.validateDuration(duration);
		SportModality sportModality = sportsModalityCatalog.getSportModalityByName(sportModalityName);
		SportModalityValidator.modalityExists(sportModality);
		SportModalityValidator.validateDuration(duration, sportModality);

		try {
			Lesson newLesson = lessonCatalog.persistLesson(new Lesson(lessonName, sportModality, daysOfWeekString,
					LocalTime.of(startHour, startMinute), duration));
			return new LessonDTO(newLesson.getId(), newLesson.getName(), newLesson.getSportModality().getName(),
					daysOfWeekString, newLesson.getStartHour().toString(), newLesson.getEndHour().toString(),
					newLesson.getDuration(), newLesson.getStatus().toString());
		} catch (Exception e) {
			throw new ApplicationException("Error creating new lesson", e);
		}
	}

	private List<SportModalityDTO> getSportModalities(List<SportModality> entityList) {
		List<SportModalityDTO> result = new ArrayList<>();
		for (SportModality sportModalityEntity : entityList)
			result.add(new SportModalityDTO(sportModalityEntity.getId(), sportModalityEntity.getName(),
					sportModalityEntity.getMinDuration(), sportModalityEntity.getCostPerHour()));
		return result;
	}

}
