package facade.startup;

import business.catalogs.FacilityCatalog;
import business.catalogs.LessonCatalog;
import business.catalogs.RegistrationCatalog;
import business.catalogs.SportModalityCatalog;
import business.catalogs.UserCatalog;
import business.handlers.ActivateLessonHandler;
import business.handlers.CreateLessonHandler;
import business.handlers.RegisterLessonHandler;
import business.handlers.ShowOccupationHandler;

public class AulaGes {

    private CreateLessonHandler createLessonHandler;
    private ActivateLessonHandler activateLessonHandler;
    private RegisterLessonHandler registerLessonHandler;
    private ShowOccupationHandler showOccupationHandler;

    public AulaGes() {
        LessonCatalog lessonCatalog = new LessonCatalog();
        SportModalityCatalog sportsModalityCatalog = new SportModalityCatalog();
        FacilityCatalog facilityCatalog = new FacilityCatalog();
        RegistrationCatalog registrationCatalog = new RegistrationCatalog();
        UserCatalog userCatalog = new UserCatalog();
        createLessonHandler = new CreateLessonHandler(lessonCatalog, sportsModalityCatalog);
        activateLessonHandler = new ActivateLessonHandler(lessonCatalog, facilityCatalog);
        registerLessonHandler = new RegisterLessonHandler(sportsModalityCatalog, lessonCatalog, registrationCatalog,
                userCatalog, facilityCatalog);
        showOccupationHandler = new ShowOccupationHandler(facilityCatalog);
    }

    public CreateLessonHandler getCreateLessonHandler() {
        return createLessonHandler;
    }

    public ActivateLessonHandler getActivateLessonHandler() {
        return activateLessonHandler;
    }

    public RegisterLessonHandler getRegisterLessonHandler() {
        return registerLessonHandler;
    }

    public ShowOccupationHandler getShowOccupationHandler() {
        return showOccupationHandler;
    }
}
