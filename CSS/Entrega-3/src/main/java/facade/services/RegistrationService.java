package facade.services;

import business.handlers.RegisterLessonHandler;
import facade.exceptions.ApplicationException;

public class RegistrationService {

    private RegisterLessonHandler registrationLessonHandler;

    public RegistrationService(RegisterLessonHandler registrationLessonHandler) {
		this.registrationLessonHandler = registrationLessonHandler;
    }

    public void newRegistration() {
        registrationLessonHandler.newRegistration();
    }

    public void chooseSportAndRegistrationType(String sportModalityName, int registrationType) throws ApplicationException {
        registrationLessonHandler.chooseSportAndRegistrationType(sportModalityName, registrationType);
    }

    public void chooseLesson(String lessonName, int userId) throws ApplicationException {
        registrationLessonHandler.chooseLesson(lessonName, userId);
    }

}
