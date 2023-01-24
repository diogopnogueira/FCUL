package business.user;

import business.lesson.Lesson;
import business.registration.Registration;
import facade.exceptions.InvalidUserRegistrationException;
import facade.exceptions.UserNotFoundException;

public class UserValidator {

	private UserValidator() {
	}
	
	public static void userHasAlreadyRegisteredOnLesson(Lesson lesson, User user) throws InvalidUserRegistrationException {
		for (Registration userRegistration : user.getRegistrations())
			if (userRegistration.getLesson().getName().equals(lesson.getName()))
				throw new InvalidUserRegistrationException("Invalid user registration! User has already registered on " + lesson.getName());
	}

	public static void userExists(User user) throws UserNotFoundException {
		if (user == null)
			throw new UserNotFoundException("Invalid user id! User not found!");
	}

}
