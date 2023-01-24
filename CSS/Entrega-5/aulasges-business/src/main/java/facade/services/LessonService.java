package facade.services;

import java.time.LocalDate;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.ActivateLessonHandler;
import business.handlers.CreateLessonHandler;
import facade.dto.ActiveLessonDTO;
import facade.dto.FacilityDTO;
import facade.dto.LessonDTO;
import facade.dto.SportModalityDTO;
import facade.exceptions.ApplicationException;
import facade.remote.LessonServiceRemote;

@Stateless
public class LessonService implements LessonServiceRemote {

	@EJB
	private CreateLessonHandler createLessonHandler;

	@EJB
	private ActivateLessonHandler activateLessonHandler;

	@Override
	public Collection<SportModalityDTO> newLesson() throws ApplicationException {
		return createLessonHandler.newLesson();
	}

	@Override
	public LessonDTO addLesson(String sportModalityName, String lessonName, String[] daysOfWeek, int startHour,
			int startMinute, int duration) throws ApplicationException {
		return createLessonHandler.addLesson(sportModalityName, lessonName, daysOfWeek, startHour, startMinute,
				duration);
	}

	@Override
	public Collection<FacilityDTO> newActivation() throws ApplicationException {
		return activateLessonHandler.newActivation();
	}

	@Override
	public ActiveLessonDTO activateLesson(String lessonName, String facilityName, LocalDate startDate,
			LocalDate endDate, int participantsNumber) throws ApplicationException {
		return activateLessonHandler.activateLesson(lessonName, facilityName, startDate, endDate, participantsNumber);
	}

}
