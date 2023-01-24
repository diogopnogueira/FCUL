package business.catalogs;

import business.lesson.Lesson;
import business.lesson.LessonStatus;
import business.sportModality.SportModality;
import facade.exceptions.ApplicationException;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class LessonCatalog {

    private EntityManager entityManager;

    public LessonCatalog(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Lesson getLessonByName(String lessonName) throws ApplicationException {
        TypedQuery<Lesson> query = entityManager.createNamedQuery(Lesson.FIND_BY_NAME, Lesson.class);
        query.setParameter(Lesson.LESSON_NAME, lessonName);
        try {
            return query.getSingleResult();
        } catch (PersistenceException e) {
            throw new ApplicationException("Lesson not found.", e);
        }
    }

    public void persistLesson(Lesson lesson) {
        entityManager.persist(lesson);
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
