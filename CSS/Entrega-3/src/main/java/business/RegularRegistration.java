package business;

public class RegularRegistration extends Registration {

    @Override
    public boolean isValid(SportModality sportModality, Lesson lesson) {
        return lesson.getSportModality().equals(sportModality) && lesson.getVacancies() != 0;
    }

    @Override
    public double getCost() {
        return getLesson().getSportModality().getCostPerHour() * getLesson().getDuration() / 60 *
                getLesson().getDaysOfWeek().size() * 4;
    }

}
