package business;

import java.time.LocalTime;
import java.util.*;

public class Facility {

    private String name;
    private int maxCapacity;
    private List<Occupation> occupations;
    private List<SportModality> sportModalities;

    public Facility(String name, int maxCapacity, List<SportModality> sportModalities) {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.occupations = new ArrayList<>();
        this.sportModalities = sportModalities;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public List<Occupation> getOccupations() {
        return occupations;
    }

    public void addOccupation(Occupation occupation) {
        occupations.add(occupation);
    }

    public boolean allowsSportModality(SportModality sportModality) {
        for (SportModality modality : sportModalities)
            if (modality.getName().equals(sportModality.getName()))
                return true;
        return false;
    }

    public boolean isAvailable(Lesson lesson) {
        for (Occupation occupation : occupations) {
            boolean checkDaysOfWeek = !Collections.disjoint(occupation.getLesson().getDaysOfWeek(), lesson.getDaysOfWeek());
            boolean checkLessonStartHour = hourIsBetween(lesson.getStartHour(), occupation.getLesson().getStartHour(), occupation.getLesson().getEndHour());
            boolean checkLessonEndHour = hourIsBetween(lesson.getEndHour(), occupation.getLesson().getStartHour(), occupation.getLesson().getEndHour());
            if (checkDaysOfWeek && (checkLessonStartHour || checkLessonEndHour))
                return false;
        }
        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private boolean hourIsBetween(LocalTime dateToCompare, LocalTime startDate, LocalTime endDate) {
        return dateToCompare.isAfter(startDate) && dateToCompare.isBefore(endDate);
    }


}
