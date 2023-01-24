package business.handlers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.catalogs.*;
import business.lesson.Lesson;
import business.lesson.LessonValidator;
import business.registration.Registration;
import business.registration.RegistrationFactory;
import business.registration.RegistrationValidator;
import business.sportModality.SportModality;
import business.sportModality.SportModalityValidator;
import business.user.User;
import business.user.UserValidator;
import facade.exceptions.ApplicationException;

public class RegisterLessonHandler {

	private EntityManagerFactory entityManagerFactory;

	private int currentRegistrationType;
	private SportModality currentSportModality;
	private User currentUser;


	public RegisterLessonHandler(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public Iterable<String> newRegistration() throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		SportModalityCatalog sportsModalityCatalog = new SportModalityCatalog(entityManager);

		try {
			entityManager.getTransaction().begin();
			Iterable<String> result = sportsModalityCatalog.getAllSportModalities();
			entityManager.getTransaction().commit();
			return result;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error fetching sport modalities!", e);
		} finally {
			entityManager.close();
		}
	}

	public String chooseSportAndRegistrationType(String sportModalityName, int registrationType) throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		SportModalityCatalog sportModalityCatalog = new SportModalityCatalog(entityManager);
		LessonCatalog lessonCatalog = new LessonCatalog(entityManager);

		try {
			entityManager.getTransaction().begin();
			currentSportModality = sportModalityCatalog.getSportModalityByName(sportModalityName);

			SportModalityValidator.modalityExists(currentSportModality);
			RegistrationValidator.validateRegistrationType(registrationType);

			currentRegistrationType = registrationType;

			// Caso se use a data atual utilizar o metodo abaixo:
			//			checkActiveLessons();

			List<Lesson> activeLessons = lessonCatalog.getActiveLessonsBySportModality(currentSportModality);
			LessonValidator.checkActiveLessonsOnSportModality(activeLessons);

			entityManager.getTransaction().commit();
			return showActiveLessons(activeLessons);
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error fetching active lessons!", e);
		} finally {
			entityManager.close();
		}
	}

	public void chooseUserId(int userId) throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		UserCatalog userCatalog = new UserCatalog(entityManager);

		try {
			entityManager.getTransaction().begin();
			User user = userCatalog.getUserByUserId(userId);
			UserValidator.userExists(user);
			currentUser = user;
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error choosing userID!", e);
		} finally {
			entityManager.close();
		}
	}


	public String chooseLesson(String lessonName) throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		LessonCatalog lessonCatalog = new LessonCatalog(entityManager);
		RegistrationCatalog registrationCatalog = new RegistrationCatalog(entityManager);

		try {
			entityManager.getTransaction().begin();
			Lesson lesson = lessonCatalog.getLessonByName(lessonName);

			LessonValidator.lessonExists(lesson);
			LessonValidator.lessonHasSameSportModality(lesson, currentSportModality);
			LessonValidator.lessonIsActive(lesson);
			LessonValidator.lessonHasVacancies(currentRegistrationType, lesson);
			UserValidator.userHasAlreadyRegisteredOnLesson(lesson, currentUser);

			Registration registration = RegistrationFactory.createRegistration(currentRegistrationType);
			registration.setUser(currentUser);

			RegistrationValidator.registrationIsValidOnLesson(registration, lesson);

			lesson.decrementVacancies();
			lesson.getRegistrations().add(registration);

			registration.setLesson(lesson);
			registrationCatalog.persistRegistration(registration);
			entityManager.getTransaction().commit();
			return lessonName + " price: " + registration.getCost() + " euros\n";
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error creating registration!", e);
		} finally {
			entityManager.close();
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	private String showActiveLessons(List<Lesson> activeLessons) {
		StringBuilder result = new StringBuilder();

		for (Lesson lesson : activeLessons) {
			if (currentRegistrationType == 0 && lesson.getVacancies() > 0)
				result.append(lesson.toString()).append("\n");
			if (currentRegistrationType == 1 && lesson.getNumberOfRegularRegistrations() < lesson.getMaxParticipants())
				result.append(lesson.toString()).append("\n");
		}
		if (result.toString().isEmpty())
			result.append(("No lessons to show\n"));

		return result.toString();
	}

	/* Caso se use a data atual verifica se alguma aula ativa passou o prazo de ativação. Em caso afirmativo desativa a aula.
	private void checkActiveLessons() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		FacilityCatalog facilityCatalog = new FacilityCatalog(entityManager);
		try {
			entityManager.getTransaction().begin();
			for (Facility facility : facilityCatalog.getAllFacilities())
				for (Occupation occupation : facility.getOccupations())
					if (CurrentDate.getCurrentDate().isAfter(occupation.getEndDate().atTime(occupation.getLesson().getEndHour()))) {
						occupation.getLesson().setStatus(LessonStatus.INACTIVE);
						occupation.getLesson().setVacancies(0);
					}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
		} finally {
			entityManager.close();
		}
	}*/

}
