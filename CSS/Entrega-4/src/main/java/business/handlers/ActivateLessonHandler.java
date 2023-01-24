package business.handlers;

import business.lesson.Lesson;
import business.lesson.LessonValidator;
import business.occupation.Occupation;
import business.occupation.OccupationValidator;
import business.catalogs.FacilityCatalog;
import business.catalogs.LessonCatalog;
import business.facility.Facility;
import business.facility.FacilityValidator;
import facade.exceptions.ApplicationException;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ActivateLessonHandler {

	private EntityManagerFactory entityManagerFactory;

	public ActivateLessonHandler(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public Iterable<String> newActivation() throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		FacilityCatalog facilityCatalog = new FacilityCatalog(entityManager);

		try {
			entityManager.getTransaction().begin();
			Iterable<String> result = facilityCatalog.getAllFacilityNames();
			entityManager.getTransaction().commit();
			return result;
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error fetching facilities", e);
		} finally {
			entityManager.close();
		}

	}

	public void activateLesson(String lessonName, String facilityName, LocalDate startDate, LocalDate endDate,
			int maxParticipants) throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		FacilityCatalog facilityCatalog = new FacilityCatalog(entityManager);
		LessonCatalog lessonCatalog = new LessonCatalog(entityManager);

		try {
			entityManager.getTransaction().begin();
			Lesson lesson = lessonCatalog.getLessonByName(lessonName);
			Facility facility = facilityCatalog.getFacilityByName(facilityName);

			LessonValidator.lessonExists(lesson);
			LessonValidator.lessonIsAlreadyActive(lesson);
			FacilityValidator.facilityExists(facility);
			FacilityValidator.facilityAllowsSportModality(facility, lesson);
			FacilityValidator.facilityIsAvailable(facility, lesson);
			FacilityValidator.validateMaxParticipantsOnFacility(facility, maxParticipants);
			OccupationValidator.validateOccupationDate(startDate, endDate);

			facility.addOccupation(new Occupation(startDate, endDate, lesson));
			lesson.activate(facility, maxParticipants);
			lessonCatalog.persistLesson(lesson);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error activating new lesson", e);
		} finally {
			entityManager.close();
		}
	}

}