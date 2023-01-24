package business.registration;

import business.lesson.Lesson;
import facade.exceptions.LessonNotAllowedOnRegistrationException;
import facade.exceptions.RegistrationTypeNotFoundException;

public class RegistrationValidator {

	private RegistrationValidator() {
	}

	public static void registrationIsValidOnLesson(Registration registration, Lesson lesson)
			throws LessonNotAllowedOnRegistrationException {
		if (!registration.isValid(lesson.getSportModality(), lesson))
			throw new LessonNotAllowedOnRegistrationException(
					"Invalid registration! Lesson " + lesson.getName() + " is not allowed on that registration type!");
	}

	public static void validateRegistrationType(String registrationType) throws RegistrationTypeNotFoundException {
		if (!registrationType.equals("avulso") && !registrationType.equals("regular"))
			throw new RegistrationTypeNotFoundException(
					"Invalid registration type! Registration Type " + registrationType + "  not found!");
	}
}
