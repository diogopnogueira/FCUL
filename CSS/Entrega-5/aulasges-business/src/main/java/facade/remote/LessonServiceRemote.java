package facade.remote;

import java.time.LocalDate;
import java.util.Collection;

import javax.ejb.Remote;

import facade.dto.ActiveLessonDTO;
import facade.dto.FacilityDTO;
import facade.dto.LessonDTO;
import facade.dto.SportModalityDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface LessonServiceRemote {

	Collection<SportModalityDTO> newLesson() throws ApplicationException;

	LessonDTO addLesson(String sportModalityName, String lessonName, String[] daysOfWeek, int startHour,
			int startMinute, int duration) throws ApplicationException;

	Collection<FacilityDTO> newActivation() throws ApplicationException;

	ActiveLessonDTO activateLesson(String lessonName, String facilityName, LocalDate startDate, LocalDate endDate,
			int participantsNumber) throws ApplicationException;

}
