package business.handlers;

import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import business.catalogs.LessonCatalog;
import business.lesson.Lesson;
import business.lesson.LessonValidator;
import business.sportModality.SportModality;
import business.sportModality.SportModalityValidator;
import business.catalogs.SportModalityCatalog;
import facade.exceptions.ApplicationException;

public class CreateLessonHandler {

	private EntityManagerFactory entityManagerFactory;

	public CreateLessonHandler(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public Iterable<String> newLesson() throws ApplicationException {
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
			throw new ApplicationException("Error fetching sport modalities", e);
		} finally {
			entityManager.close();
		}
	}

	public void addLesson(String sportModalityName, String lessonName, String[] daysOfWeekString,
			int startHour, int startMinute, int duration) throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		SportModalityCatalog sportsModalityCatalog = new SportModalityCatalog(entityManager);
		LessonCatalog lessonCatalog = new LessonCatalog(entityManager);

		try {
			entityManager.getTransaction().begin();
			LessonValidator.validateLessonName(lessonName);
			LessonValidator.validateDuration(duration);
			
			SportModality sportModality = sportsModalityCatalog.getSportModalityByName(sportModalityName);

			SportModalityValidator.modalityExists(sportModality);
			SportModalityValidator.validateDuration(duration, sportModality);
			
			lessonCatalog.persistLesson(new Lesson(lessonName, sportModality, daysOfWeekString,
					LocalTime.of(startHour, startMinute), duration));
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error creating new lesson", e);
		} finally {
			entityManager.close();
		}

	}

}



