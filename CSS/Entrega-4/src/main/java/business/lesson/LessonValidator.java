package business.lesson;

import java.util.List;

import business.lesson.exceptions.InvalidDurationException;
import business.lesson.exceptions.InvalidLessonNameException;
import business.lesson.exceptions.InvalidLessonSportModalityException;
import business.lesson.exceptions.InvalidLessonStatusException;
import business.lesson.exceptions.LessonNotFoundException;
import business.lesson.exceptions.NoVacanciesException;
import business.sportModality.SportModality;

public class LessonValidator {

	private static final String VALID_LESSON_NAME_REGEX = "^(?=(.*[0-9]){3})[a-zA-Z0-9]{6}$";

	private LessonValidator() {
	}

	public static void validateLessonName(String lessonName) throws InvalidLessonNameException {
		if (!lessonName.matches(VALID_LESSON_NAME_REGEX))
			throw new InvalidLessonNameException("Invalid lesson name! Please choose a name with: \n"
					+ "- 6 characters \n"
					+ "- at least 3 numbers \n"
					+ "- no blank spaces");
	}

	public static void validateDuration(int duration) throws InvalidDurationException {
		if (duration <= 0)
			throw new InvalidDurationException("Invalid duration! Duration must be greater than 0");
	}

	public static void lessonExists(Lesson lesson) throws LessonNotFoundException {
		if (lesson == null)
			throw new LessonNotFoundException("Invalid Lesson! Lesson not found!");
	}

	public static void lessonIsAlreadyActive(Lesson lesson) throws InvalidLessonStatusException {
		if (lesson.isActive())
			throw new InvalidLessonStatusException("Lesson is already active");
	}

	public static void lessonIsActive(Lesson lesson) throws InvalidLessonStatusException {
		if (!lesson.isActive())
			throw new InvalidLessonStatusException("Lesson is not active");
	}

	public static void checkActiveLessonsOnSportModality(List<Lesson> activeLessons) throws InvalidLessonStatusException  {
		if (activeLessons.isEmpty())
			throw new InvalidLessonStatusException("There are no active lessons for that Sport Modality");
	}

	public static void lessonHasSameSportModality(Lesson lesson, SportModality sportModality) throws InvalidLessonSportModalityException {
		if (!lesson.getSportModality().getName().equals(sportModality.getName()))
			throw new InvalidLessonSportModalityException("Invalid lesson! Please pick a lesson of the sport modality previously chosen!");
	}

	public static void lessonHasVacancies(int registrationType, Lesson lesson) throws NoVacanciesException  {
		if ((registrationType == 1 && lesson.getNumberOfRegularRegistrations() >= lesson.getMaxParticipants()) ||
				(registrationType == 0 && lesson.getVacancies() == 0))
			throw new NoVacanciesException("Invalid lesson! There are no vacancies for this lesson");
	}


}
