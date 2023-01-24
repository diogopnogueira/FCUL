package business.catalogs;

import business.Facility;
import business.Lesson;
import business.LessonStatus;
import business.SportModality;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class LessonCatalog {

    private List<Lesson> lessons;

    public LessonCatalog() {
        lessons = new LinkedList<>();
    }

    public Lesson getLessonByName(String lessonName) {
        for (Lesson lesson : lessons)
            if (lesson.getName().equals(lessonName))
                return lesson;
        return null;
    }

    public void addLesson(String lessonName, SportModality sportModality, List<DayOfWeek> daysOfWeek,
                          LocalTime startHour, int duration) {
        lessons.add(new Lesson(lessonName, sportModality, daysOfWeek, startHour, duration));
    }

    public void activateLesson(Lesson lesson, Facility facility) {
        lesson.setFacility(facility);
        lesson.setVacancies(facility.getMaxCapacity());
        lesson.setStatus(LessonStatus.ACTIVE);
        lessons.set(lessons.indexOf(lesson), lesson);
    }

    public List<Lesson> getActiveLessonsBySportModality(SportModality sportModality) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson lesson : lessons)
            if (lesson.isActive() && lesson.getSportModality().getName().equals(sportModality.getName()))
                result.add(lesson);
        return result;
    }

    public void updateLesson(Lesson lesson) {
        lessons.set(lessons.indexOf(lesson), lesson);
    }

}
