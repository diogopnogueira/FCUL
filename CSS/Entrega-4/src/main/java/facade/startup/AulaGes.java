package facade.startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.handlers.ActivateLessonHandler;
import business.handlers.CreateLessonHandler;
import business.handlers.RegisterLessonHandler;
import business.handlers.ShowOccupationHandler;
import facade.exceptions.ApplicationException;
import facade.services.FacilityService;
import facade.services.LessonService;
import facade.services.RegistrationService;

public class AulaGes {
	
	private EntityManagerFactory emf;
	private LessonService lessonService;
	private FacilityService facilityService;
	private RegistrationService registrationService;


	public void run() throws ApplicationException {
		// Connects to the database
		try {
			emf = Persistence.createEntityManagerFactory("domain-model-jpa");
			lessonService = new LessonService(new CreateLessonHandler(emf), new ActivateLessonHandler(emf));
			facilityService = new FacilityService(new ShowOccupationHandler(emf));
			registrationService = new RegistrationService(new RegisterLessonHandler(emf));
			// exceptions thrown by JPA are not checked
		} catch (Exception e) {
			throw new ApplicationException("Error connecting database", e);
		}
	}
	
	public void stopRun() {
		// Closes the database connection
		emf.close();
	}
	
    public LessonService getLessonService() {
        return lessonService;
    }

    public FacilityService getFacilityService() {
        return facilityService;
    }
	
    public RegistrationService getRegistrationService() {
        return registrationService;
    }

}
