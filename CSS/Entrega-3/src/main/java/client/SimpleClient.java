package client;

import java.time.LocalDate;

import facade.exceptions.ApplicationException;
import facade.services.FacilityService;
import facade.services.LessonService;
import facade.services.RegistrationService;
import facade.startup.AulaGes;

public class SimpleClient {

	private LessonService lessonService;
	private RegistrationService registrationService;
	private FacilityService facilityService;

	public SimpleClient(LessonService lessonService, RegistrationService registrationService,
			FacilityService facilityService) {
		this.lessonService = lessonService;
		this.registrationService = registrationService;
		this.facilityService = facilityService;
	}

	public void createLesson() {
		try {
			lessonService.newLesson();
			lessonService.addLesson("Ginasio", "Bic000", new String[]{"MONDAY", "WEDNESDAY"}, 9, 30, 120);

			lessonService.newLesson();
			lessonService.addLesson("Tenis", "Ten000", new String[]{"WEDNESDAY", "SATURDAY"}, 14, 20, 120);

			lessonService.newLesson();
			lessonService.addLesson("Natacao", "Nat000", new String[]{"TUESDAY", "FRIDAY"}, 18, 30, 120);

			lessonService.newLesson();
			lessonService.addLesson("Natacao", "Nat001", new String[]{"MONDAY", "FRIDAY"}, 9, 30, 120);
		} catch (ApplicationException e) {
			System.err.println("Error: " + e.getMessage());
			if (e.getCause() != null)
				System.err.println("Cause: ");
			e.printStackTrace();
		}
	}

	public void activateLesson() {
		try {
			LocalDate startDate1 = LocalDate.of(2020, 8, 1);
			LocalDate endDate1 = LocalDate.of(2020, 12, 31);

			
			LocalDate startDate2 = LocalDate.of(2020, 10, 19);
			LocalDate endDate2 = LocalDate.of(2021, 1, 3);
			
			
			LocalDate startDate3 = LocalDate.of(2020, 7, 4);
			LocalDate endDate3 = LocalDate.of(2020, 12, 1);
			
			
			LocalDate startDate4 = LocalDate.of(2020, 12, 1);
			LocalDate endDate4 = LocalDate.of(2020, 12, 7);
			
			lessonService.newActivation();
			lessonService.activateLesson("Bic000", "Sala de Bicicletas", startDate1 , endDate1, 12);

			lessonService.newActivation();
			lessonService.activateLesson("Ten000", "Campo de Tenis", startDate2, endDate2, 4);

			lessonService.newActivation();
			lessonService.activateLesson("Nat000", "Piscina", startDate3, endDate3, 5);

			lessonService.newActivation();
			lessonService.activateLesson("Nat001", "Piscina", startDate4, endDate4, 5);
		} catch (ApplicationException e) {
			System.err.println("Error: " + e.getMessage());
			if (e.getCause() != null)
				System.err.println("Cause: ");
			e.printStackTrace();
		}

	}

	public void registerLesson() {
		try {
			registrationService.newRegistration();
			registrationService.chooseSportAndRegistrationType("Natacao", 1);
			registrationService.chooseLesson("Nat000", 12345);

			registrationService.newRegistration();
			registrationService.chooseSportAndRegistrationType("Natacao", 1);
			registrationService.chooseLesson("Nat001", 12345);

		} catch (ApplicationException e) {
			System.err.println("Error: " + e.getMessage());
			if (e.getCause() != null)
				System.err.println("Cause: ");
			e.printStackTrace();
		}
	}

	public void showOccupation() {
		try {
			facilityService.showOccupation("Piscina", 7, 8, 2020);
		} catch (ApplicationException e) {
			System.err.println("Error: " + e.getMessage());
			if (e.getCause() != null)
				System.err.println("Cause: ");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		AulaGes app = new AulaGes();

		LessonService lessonService = new LessonService(app.getCreateLessonHandler(), app.getActivateLessonHandler());
		RegistrationService registrationService = new RegistrationService(app.getRegisterLessonHandler());
		FacilityService facilityService = new FacilityService(app.getShowOccupationHandler());

		SimpleClient simpleClient = new SimpleClient(lessonService, registrationService, facilityService);
		simpleClient.createLesson();
		simpleClient.activateLesson();
		simpleClient.registerLesson();
		simpleClient.showOccupation();
	}
}
