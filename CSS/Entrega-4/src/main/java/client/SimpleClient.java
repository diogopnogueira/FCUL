package client;

import facade.exceptions.ApplicationException;
import facade.services.FacilityService;
import facade.services.LessonService;
import facade.services.RegistrationService;

import java.time.LocalDate;

import business.CurrentDate;
import business.facility.exceptions.FacilityNotFoundException;
import business.sportModality.exceptions.SportModalityNotFoundException;
import business.user.exceptions.UserNotFoundException;
import facade.startup.AulaGes;

public class SimpleClient {

	/**
	 * An utility class should not have public constructors
	 */
	private SimpleClient() {
	}

	public static void main(String[] args) throws SportModalityNotFoundException, FacilityNotFoundException, UserNotFoundException {
		AulaGes app = new AulaGes();

		try {
			app.run();
			LessonService lessonService = app.getLessonService();
			RegistrationService registrationService = app.getRegistrationService();
			FacilityService facilityService = app.getFacilityService();

			//1
			System.out.println("Teste 1");
			System.out.println(lessonService.newLesson());
			lessonService.addLesson("Pilates", "PLT001", new String[]{"TUESDAY", "THURSDAY"}, 9, 15, 55);

			//2
			System.out.println("Teste 2");
			System.out.println(lessonService.newLesson());
			lessonService.addLesson("Pilates", "PLT002", new String[]{"TUESDAY", "THURSDAY"}, 12, 15, 55);

			//3
			System.out.println("Teste 3");
			System.out.println(lessonService.newLesson());
			lessonService.addLesson("Pilates", "PLT003", new String[]{"MONDAY", "WEDNESDAY", "FRIDAY"}, 12, 15, 55);

			//4 - Suposto dar ERRO
//			System.out.println("Teste 4");
//			System.out.println(lessonService.newLesson());
//			lessonService.addLesson("GAP", "GAP001", new String[]{"MONDAY", "WEDNESDAY", "FRIDAY"}, 9, 0, 45);  

			//5
			System.out.println("Teste 5");
			System.out.println(lessonService.newLesson());
			lessonService.addLesson("GAP", "GAP001", new String[]{"MONDAY", "WEDNESDAY", "FRIDAY"}, 9, 0, 50);

			//6
			
			System.out.println("Teste 6");
			System.out.println(lessonService.newLesson());
			lessonService.addLesson("STEP", "STP001", new String[]{"MONDAY", "WEDNESDAY", "FRIDAY"}, 9, 15, 45);

			//7
			System.out.println("Teste 7");
			System.out.println(lessonService.newActivation());
			lessonService.activateLesson("PLT001", "Estudio 1", CurrentDate.getCurrentDate().toLocalDate(),
					LocalDate.of(2020, 7, 31), 2);

			//8
			System.out.println("Teste 8");
			System.out.println(lessonService.newActivation());
			lessonService.activateLesson("PLT002", "Estudio 1", CurrentDate.getCurrentDate().toLocalDate(),
					LocalDate.of(2020, 7, 31), 2);

			//9
			System.out.println("Teste 9");
			System.out.println(lessonService.newActivation());
			lessonService.activateLesson("GAP001", "Estudio 1", CurrentDate.getCurrentDate().toLocalDate(),
					LocalDate.of(2020, 7, 31), 2);

			//10 - Suposto dar ERRO
//			System.out.println("Teste 10");
//			System.out.println(lessonService.newActivation());
//			lessonService.activateLesson("STP001", "Estudio 2", CurrentDate.getCurrentDate().toLocalDate(),
//					LocalDate.of(2020, 7, 31), 2);

			//11 - Suposto dar ERRO
//			System.out.println("Teste 11");
//			System.out.println(lessonService.newActivation());
//			lessonService.activateLesson("STP001", "Estudio 1", CurrentDate.getCurrentDate().toLocalDate(),
//					LocalDate.of(2020, 7, 31), 2);

			//12
			System.out.println("Teste 12");
			System.out.println(registrationService.newRegistration());
			System.out.println();
			System.out.println(registrationService.chooseSportAndRegistrationType("Pilates", 1));
			registrationService.chooseUserId(1);
			System.out.println(registrationService.chooseLesson("PLT001"));

			//13
			System.out.println("Teste 13");
			System.out.println(registrationService.newRegistration());
			System.out.println();
			System.out.println(registrationService.chooseSportAndRegistrationType("Pilates", 1));
			registrationService.chooseUserId(3);
			System.out.println(registrationService.chooseLesson("PLT002"));

			//14
			System.out.println("Teste 14");
			System.out.println(registrationService.newRegistration());
			System.out.println();
			System.out.println(registrationService.chooseSportAndRegistrationType("Pilates", 1));
			registrationService.chooseUserId(2);
			System.out.println(registrationService.chooseLesson("PLT001"));

			//15
			System.out.println("Teste 15");
			System.out.println(registrationService.newRegistration());
			System.out.println();
			System.out.println(registrationService.chooseSportAndRegistrationType("Pilates", 0));
			registrationService.chooseUserId(4);
			System.out.println(registrationService.chooseLesson("PLT002"));

			//16
			System.out.println("Teste 16");
			System.out.println(registrationService.newRegistration());
			System.out.println();
			System.out.println(registrationService.chooseSportAndRegistrationType("Pilates", 0));
			registrationService.chooseUserId(5);
			
			//17
			System.out.println("Teste 17");
			System.out.println(registrationService.newRegistration());
			System.out.println();
			System.out.println(registrationService.chooseSportAndRegistrationType("Pilates", 1));
			registrationService.chooseUserId(5);
			System.out.println(registrationService.chooseLesson("PLT002"));

			//18
			System.out.println("Teste 18");
			System.out.println(facilityService.showOccupation("Estudio 1", CurrentDate.getCurrentDate().getDayOfMonth()+1,
					CurrentDate.getCurrentDate().getMonthValue(), CurrentDate.getCurrentDate().getYear()));


		} catch (ApplicationException e) {
			System.out.println("Error: " + e.getMessage());
			if (e.getCause() != null) {
				System.out.println("Cause: ");
			}
			e.printStackTrace();
		}
	}


}
