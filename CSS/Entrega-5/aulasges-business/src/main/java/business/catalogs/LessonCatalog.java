package business.catalogs;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.lesson.Lesson;
import business.lesson.LessonStatus;
import business.sportModality.SportModality;
import facade.exceptions.ApplicationException;

@Stateless
public class LessonCatalog {

	@PersistenceContext
	private EntityManager entityManager;

	public Lesson getLessonByName(String lessonName) throws ApplicationException {
		TypedQuery<Lesson> query = entityManager.createNamedQuery(Lesson.FIND_BY_NAME, Lesson.class);
		query.setParameter(Lesson.LESSON_NAME, lessonName);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("Lesson not found.", e);
		}
	}

	@Transactional
	public Lesson persistLesson(Lesson lesson) {
		Lesson newLesson = lesson;
		entityManager.persist(newLesson);
		return newLesson;
	}

	public List<Lesson> getActiveLessonsBySportModality(SportModality sportModality) throws ApplicationException {
		TypedQuery<Lesson> query = entityManager.createNamedQuery(Lesson.FIND_ACTIVE_BY_SPORTMODALITY, Lesson.class);
		query.setParameter(Lesson.LESSON_STATUS, LessonStatus.ACTIVE);
		query.setParameter(Lesson.LESSON_SPORTMODALITY, sportModality);
		try {
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new ApplicationException("Active lessons not found.", e);
		}
	}

}
