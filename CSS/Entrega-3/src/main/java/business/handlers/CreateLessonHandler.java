package business.handlers;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import business.catalogs.LessonCatalog;
import business.SportModality;
import business.catalogs.SportModalityCatalog;
import facade.exceptions.ApplicationException;

public class CreateLessonHandler {

    private LessonCatalog lessonCatalog;
    private SportModalityCatalog sportsModalityCatalog;
    private static final String VALID_LESSON_NAME_REGEX = "^(?=(.*[0-9]){3})[a-zA-Z0-9]{6}$";

    public CreateLessonHandler(LessonCatalog lessonCatalog, SportModalityCatalog sportsModalityCatalog) {
        this.lessonCatalog = lessonCatalog;
        this.sportsModalityCatalog = sportsModalityCatalog;
    }

    public void newLesson() {
        System.out.println("-----------------UC1: Create Lesson!-----------------\n");
        List<SportModality> sportModalities = sportsModalityCatalog.getAllSportModalities();
        showSportModalityNames(sportModalities);
    }

    public void addLesson(String sportModalityName, String lessonName, String[] daysOfWeekString,
                          int startHour, int startMinute, int duration) throws ApplicationException {
        validateLessonName(lessonName);
        SportModality sportModality = sportsModalityCatalog.getSportModalityByName(sportModalityName);
        validateModality(duration, sportModality);
        lessonCatalog.addLesson(lessonName, sportModality, getDaysOfWeek(daysOfWeekString),
                LocalTime.of(startHour, startMinute), duration);
        System.out.println("Lesson " + lessonName + " created successfully!\n");
        System.out.println("-----------------End of UC1-----------------\n\n");
    }

    //////////////////////////////////////////////VERIFICATIONS//////////////////////////////////////////////

    private void validateLessonName(String lessonName) throws ApplicationException {
        lessonNameExists(lessonName);
        lessonNameMatches(lessonName);
    }

    private void validateModality(int duration, SportModality sportModality) throws ApplicationException {
        modalityExists(sportModality);
        validateDuration(duration, sportModality);
    }

    private void validateDuration(int duration, SportModality sportModality) throws ApplicationException {
        durationIsPositive(duration);
        durationGreaterOrEqual(duration, sportModality);
    }

    private void lessonNameExists(String lessonName) throws ApplicationException {
        if (lessonCatalog.getLessonByName(lessonName) != null)
            throw new ApplicationException("Lesson " + lessonName + " already exists!");
    }

    private void lessonNameMatches(String lessonName) throws ApplicationException {
        if (!lessonName.matches(VALID_LESSON_NAME_REGEX))
            throw new ApplicationException("Invalid lesson name! Please choose a name with: \n"
                    + "- 6 characters \n"
                    + "- at least 3 numbers \n"
                    + "- no blank spaces");
    }

    private void modalityExists(SportModality sportModality) throws ApplicationException {
        if (sportModality == null)
            throw new ApplicationException("Sport modality does not exist");
    }


    private void durationIsPositive(int duration) throws ApplicationException {
        if (duration <= 0)
            throw new ApplicationException("Invalid duration! Duration must be greater than 0");
    }

    private void durationGreaterOrEqual(int duration, SportModality sportModality) throws ApplicationException {
        if (duration < sportModality.getMinDuration())
            throw new ApplicationException("Invalid duration! Must be greater or equal than sport"
                    + " modality minimum duration");
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////

    private void showSportModalityNames(List<SportModality> sportModalities) {
        System.out.print("Sport Modalities available: ");
        for (SportModality sportModality : sportModalities)
            System.out.print(sportModality.getName() + ", ");
        System.out.println("\n");
    }

    private List<DayOfWeek> getDaysOfWeek(String[] daysOfWeek) {
        List<DayOfWeek> result = new ArrayList<>();
        for (String day : daysOfWeek)
            result.add(DayOfWeek.valueOf(day));
        return result;
    }

}



