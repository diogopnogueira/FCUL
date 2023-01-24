package facade.services;

import business.sportModality.exceptions.SportModalityNotFoundException;
import business.user.exceptions.UserNotFoundException;
import business.handlers.RegisterLessonHandler;
import facade.exceptions.ApplicationException;

public class RegistrationService {

    private RegisterLessonHandler registrationLessonHandler;

    public RegistrationService(RegisterLessonHandler registrationLessonHandler) {
        this.registrationLessonHandler = registrationLessonHandler;
    }

    public Iterable<String> newRegistration() throws ApplicationException {
        return registrationLessonHandler.newRegistration();
    }

    public String chooseSportAndRegistrationType(String sportModalityName, int registrationType) throws ApplicationException, SportModalityNotFoundException {
        return registrationLessonHandler.chooseSportAndRegistrationType(sportModalityName, registrationType);
    }

    public void chooseUserId(int userId) throws ApplicationException, UserNotFoundException {
        registrationLessonHandler.chooseUserId(userId);
    }

    public String chooseLesson(String lessonName) throws ApplicationException, UserNotFoundException {
        return registrationLessonHandler.chooseLesson(lessonName);
    }


}
