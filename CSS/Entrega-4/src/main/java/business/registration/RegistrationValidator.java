package business.registration;

import business.lesson.Lesson;
import business.registration.exceptions.LessonNotAllowedOnRegistrationException;
import business.registration.exceptions.RegistrationTypeNotFoundException;

public class RegistrationValidator {
	
	private RegistrationValidator() {
	}

	public static void registrationIsValidOnLesson(Registration registration, Lesson lesson) throws LessonNotAllowedOnRegistrationException {
		if (!registration.isValid(lesson.getSportModality(), lesson))
			throw new LessonNotAllowedOnRegistrationException("Invalid registration! Lesson " + lesson.getName() + " is not allowed on that registration type!");
	}

	public static void validateRegistrationType(int registrationType) throws RegistrationTypeNotFoundException {
		if (registrationType != 0 && registrationType != 1)
			throw new RegistrationTypeNotFoundException("Invalid registration type! Registration Type " + registrationType + "  not found!");
	}
}
