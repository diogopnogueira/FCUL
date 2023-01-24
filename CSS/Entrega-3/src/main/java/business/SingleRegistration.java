package business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SingleRegistration extends Registration {

    @Override
    public double getCost() {
        return getLesson().getSportModality().getCostPerHour() * getLesson().getDuration() / 60;
    }

    @Override
    public boolean isValid(SportModality sportModality, Lesson lesson) {
        return lesson.getSportModality().equals(sportModality) && lesson.getVacancies() != 0 && nextLessonIsIn24Hours(lesson);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean nextLessonIsIn24Hours(Lesson lesson) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrowWithLessonHours = now.plusDays(1).toLocalDate().atTime(lesson.getStartHour());
        DayOfWeek currentDayOfWeek = now.getDayOfWeek();
        boolean lessonToday = true;
        if (lesson.getDaysOfWeek().contains(currentDayOfWeek))
            lessonToday = now.until(now.toLocalDate().atTime(lesson.getStartHour()), ChronoUnit.HOURS) > 0;
        boolean dias = lesson.getDaysOfWeek().contains(currentDayOfWeek.plus(1)) || lesson.getDaysOfWeek().contains(currentDayOfWeek);
        boolean horas = now.until(tomorrowWithLessonHours, ChronoUnit.HOURS) < 24 && now.until(tomorrowWithLessonHours, ChronoUnit.HOURS) > 0;
        return  lessonToday && dias && horas;
    }

}
