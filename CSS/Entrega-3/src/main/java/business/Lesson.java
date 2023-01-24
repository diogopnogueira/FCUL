package business;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class Lesson {

    private String name;
    private SportModality sportModality;
    private Facility facility;
    private List<DayOfWeek> daysOfWeek;
    private LocalTime startHour;
    private LocalTime endHour;
    private int duration;
    private LessonStatus status;
    private int vacancies;

    public Lesson(String name, SportModality sportModality, List<DayOfWeek> daysOfWeek,
                  LocalTime startHour, int duration) {
        this.name = name;
        this.sportModality = sportModality;
        this.daysOfWeek = daysOfWeek;
        this.startHour = startHour;
        this.duration = duration;
        endHour = startHour.plusMinutes(duration);
        status = LessonStatus.INACTIVE;
    }

    public String getName() {
        return name;
    }

    public SportModality getSportModality() {
        return sportModality;
    }

    public Facility getFacility() {
        return facility;
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public int getDuration() {
        return duration;
    }

    public int getVacancies() {
        return vacancies;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public void setStatus(LessonStatus status) {
        this.status = status;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    public boolean isActive() {
        return status == LessonStatus.ACTIVE;
    }

    public String toString() {
        return "Lesson name: " + name + "\n" +
                "Start hour: " + startHour.getHour() + ":" + startHour.getMinute() + "\n" +
                "End hour: " + endHour.getHour() + ":" + endHour.getMinute() + "\n" +
                "Days of Week: " + daysOfWeekToString() + "\n" +
                "Facility: " + getFacility().getName() + "\n" +
                "Vacancies: " + vacancies + "\n";
    }

    private String daysOfWeekToString() {
        StringBuilder result = new StringBuilder();
        for (DayOfWeek dayOfWeek : daysOfWeek)
            result.append(dayOfWeek.name()).append(" ");
        return result.toString();
    }


}
