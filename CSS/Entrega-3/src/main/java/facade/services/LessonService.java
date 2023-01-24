package facade.services;

import business.handlers.ActivateLessonHandler;
import business.handlers.CreateLessonHandler;
import facade.exceptions.ApplicationException;

import java.time.LocalDate;

public class LessonService {

    private CreateLessonHandler createLessonHandler;
    private ActivateLessonHandler activateLessonHandler;

    public LessonService(CreateLessonHandler createLessonHandler, ActivateLessonHandler activateLessonHandler) {
        this.createLessonHandler = createLessonHandler;
        this.activateLessonHandler = activateLessonHandler;
    }

    public void newLesson() {
        createLessonHandler.newLesson();
    }

    public void addLesson(String sportModalityName, String lessonName, String[] daysOfWeek,
                          int startHour, int startMinute, int duration) throws ApplicationException {
        createLessonHandler.addLesson(sportModalityName, lessonName, daysOfWeek, startHour, startMinute, duration);
    }

    public void newActivation() {
        activateLessonHandler.newActivation();
    }

    public void activateLesson(String lessonName, String facilityName, LocalDate startDate, LocalDate endDate,
                               int participantsNumber) throws ApplicationException {
        activateLessonHandler.activateLesson(lessonName, facilityName, startDate, endDate, participantsNumber);
    }

}
