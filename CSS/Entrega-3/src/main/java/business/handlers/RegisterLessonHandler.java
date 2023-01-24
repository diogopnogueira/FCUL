package business.handlers;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import business.*;
import business.catalogs.*;
import facade.exceptions.ApplicationException;

public class RegisterLessonHandler {

    private SportModalityCatalog sportModalityCatalog;
    private LessonCatalog lessonCatalog;
    private RegistrationCatalog registrationCatalog;
    private UserCatalog userCatalog;
    private FacilityCatalog facilityCatalog;

    private int currentRegistrationType;
    private SportModality currentSportModality;


    public RegisterLessonHandler(SportModalityCatalog sportModalityCatalog, LessonCatalog lessonCatalog,
                                 RegistrationCatalog registrationCatalog, UserCatalog userCatalog,
                                 FacilityCatalog facilityCatalog) {
        this.sportModalityCatalog = sportModalityCatalog;
        this.lessonCatalog = lessonCatalog;
        this.registrationCatalog = registrationCatalog;
        this.userCatalog = userCatalog;
        this.facilityCatalog = facilityCatalog;
    }

    public void newRegistration() {
        System.out.println("-----------------UC3: New Registration!-----------------");
        List<SportModality> sportModalities = sportModalityCatalog.getAllSportModalities();
        showSportModalities(sportModalities);
    }

    public void chooseSportAndRegistrationType(String sportModalityName, int registrationType) throws ApplicationException {
        currentSportModality = getSportModality(sportModalityName);
        validateRegistrationType(registrationType);
        showActiveLessons(currentSportModality);
    }

    public void chooseLesson(String lessonName, int userId) throws ApplicationException {
        User user = validateUserId(userId);
        Lesson lesson = validateLesson(lessonName);
        checkIfLessonAlreadyExistsOnUser(lesson, user);

        Registration addedRegistration = createRegistration(user, lesson);
        System.out.println(lessonName + " price: " + addedRegistration.getCost() + " euros\n");
        System.out.println("-----------------End of UC3-----------------\n\n");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private void showSportModalities(List<SportModality> sportModalities) {
        System.out.print("Sport Modalities available: ");
        for (SportModality sportModality : sportModalities)
            System.out.print(sportModality.getName() + ", ");
        System.out.println("\n");
    }

    private SportModality getSportModality(String sportModalityName) throws ApplicationException {
        SportModality sportModality = sportModalityCatalog.getSportModalityByName(sportModalityName);
        validateSportModality(sportModality);
        return sportModality;
    }

    private void showActiveLessons(SportModality sportModality) throws ApplicationException {
        System.out.println("Active lessons available: ");
        for (Lesson lesson : getActiveLessons(sportModality))
            if (registrationCatalog.getRegistration(currentRegistrationType).isValid(sportModality, lesson))
                System.out.print(lesson.toString() + "\n");
    }

    private List<Lesson> getActiveLessons(SportModality sportModality) throws ApplicationException {
        checkActiveLessons();
        List<Lesson> activeLessonsOnSystem = lessonCatalog.getActiveLessonsBySportModality(sportModality);
        if (activeLessonsOnSystem.isEmpty())
            throw new ApplicationException("There are no active lessons for that Sport Modality");
        activeLessonsOnSystem.sort(Comparator.comparing(Lesson::getStartHour));
        return activeLessonsOnSystem;
    }

    private void checkActiveLessons() {
        for (Facility facility : facilityCatalog.getAllFacilities())
            for (Occupation occupation : facility.getOccupations())
                if (LocalDateTime.now().isAfter(occupation.getEndDate().atTime(occupation.getLesson().getEndHour()))) {
                    occupation.getLesson().setStatus(LessonStatus.INACTIVE);
                    occupation.getLesson().setVacancies(facility.getMaxCapacity());
                    lessonCatalog.updateLesson(occupation.getLesson());
                }
    }

    private Registration createRegistration(User user, Lesson lesson) throws ApplicationException {
        Registration registrationToAdd = registrationCatalog.getRegistration(currentRegistrationType);
        registrationToAdd.setUser(user);
        registrationToAdd.setLesson(lesson);

        validateRegistration(registrationToAdd, lesson);
        addRegistration(registrationToAdd);

        return registrationToAdd;
    }

    private void addRegistration(Registration registration) {
        registration.getUser().addRegistration(registrationCatalog.getRegistration(currentRegistrationType));
        registration.getLesson().setVacancies(registration.getLesson().getVacancies() - 1);
        lessonCatalog.updateLesson(registration.getLesson());
        userCatalog.updateUser(registration.getUser());
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void validateSportModality(SportModality sportModality) throws ApplicationException {
        if (sportModality == null)
            throw new ApplicationException("Sport modality does not exist");
    }

    private void validateRegistrationType(int registrationType) throws ApplicationException {
        if (registrationCatalog.getRegistration(registrationType) == null)
            throw new ApplicationException("Registration Type " + registrationType + "  does not exist");
        currentRegistrationType = registrationType;
    }

    private User validateUserId(int userId) throws ApplicationException {
        User user = userCatalog.getUserByUserId(userId);
        if (user == null)
            throw new ApplicationException("Invalid user id: User does not exist!");
        return user;
    }

    private Lesson validateLesson(String lessonName) throws ApplicationException {
        Lesson lesson = lessonCatalog.getLessonByName(lessonName);
        if (lesson == null)
            throw new ApplicationException("Invalid lesson! Lesson does not exist!");
        if (lesson.getVacancies() == 0)
            throw new ApplicationException("Invalid lesson! There are no more vacancies for this lesson!");
        if (!lesson.getSportModality().getName().equals(currentSportModality.getName()))
            throw new ApplicationException("Invalid lesson! Please pick a lesson of the sport modality previously chosen!");
        if (!lesson.isActive())
            throw new ApplicationException("Invalid lesson! Lesson is not active!");
        return lesson;
    }

    private void checkIfLessonAlreadyExistsOnUser(Lesson lesson, User user) throws ApplicationException {
        for (Registration userRegistration : user.getRegistrations())
            if (userRegistration.getLesson().getName().equals(lesson.getName()))
                throw new ApplicationException("User has already registered on " + lesson.getName());
    }


    private void validateRegistration(Registration registrationToAdd, Lesson lesson) throws ApplicationException {
        if (!registrationToAdd.isValid(lesson.getSportModality(), lesson))
            throw new ApplicationException("Lesson " + lesson.getName() + " is not allowed on that registration type");


    }


}


