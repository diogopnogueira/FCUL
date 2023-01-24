package business.handlers;

import business.Facility;
import business.Lesson;
import business.catalogs.FacilityCatalog;
import business.catalogs.LessonCatalog;
import facade.exceptions.ApplicationException;

import java.time.LocalDate;
import java.util.List;

public class ActivateLessonHandler {

    private LessonCatalog lessonCatalog;
    private FacilityCatalog facilityCatalog;

    public ActivateLessonHandler(LessonCatalog lessonCatalog, FacilityCatalog facilityCatalog) {
        this.lessonCatalog = lessonCatalog;
        this.facilityCatalog = facilityCatalog;
    }

    public void newActivation() {
        System.out.println("-----------------UC2: Activate lesson-----------------\n");
        List<Facility> facilities = facilityCatalog.getAllFacilities();
        showFacilityNames(facilities);
    }

    public void activateLesson(String lessonName, String facilityName, LocalDate startDate, LocalDate endDate,
                               int participantsNumber) throws ApplicationException {

        Lesson lesson = lessonCatalog.getLessonByName(lessonName);
        Facility facility = facilityCatalog.getFacilityByName(facilityName);

        verifyActivation(lesson, facility, startDate, endDate, participantsNumber);

        lessonCatalog.activateLesson(lesson, facility);
        facilityCatalog.addOccupation(facility, startDate, endDate, lesson);
        System.out.println("Lesson " + lessonName + " activated successfully!\n");
        System.out.println("-----------------End of UC2-----------------\n\n");
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void verifyActivation(Lesson lesson, Facility facility, LocalDate startDate, LocalDate endDate,
                                  int participantsNumber) throws ApplicationException {
        if (lesson == null)
            throw new ApplicationException("Lesson does not exist!");

        if (lesson.isActive())
            throw new ApplicationException("Lesson is already active");

        LocalDate currentDate = LocalDate.now();
        if (currentDate.isAfter(startDate) || currentDate.isAfter(endDate) || startDate.isAfter(endDate))
            throw new ApplicationException("Invalid date! Date cannot be in the past");

        if (facility == null)
            throw new ApplicationException("Invalid facility!");

        if (!facility.allowsSportModality(lesson.getSportModality()))
            throw new ApplicationException("Facility does not allow this modality");

        if (!facility.isAvailable(lesson))
            throw new ApplicationException("Invalid date! Facility is not free");

        if (participantsNumber > facility.getMaxCapacity())
            throw new ApplicationException("Participant number is greater than facility's max capacity!");
    }

    private void showFacilityNames(List<Facility> facilities) {
        System.out.println("Facilities available: ");
        for (Facility facility : facilities)
            System.out.print(facility.getName() + ", ");
        System.out.println("\n");
    }
}
