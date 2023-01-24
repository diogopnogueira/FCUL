package business.registration;

import business.lesson.Lesson;
import business.sportModality.SportModality;
import business.user.User;

import javax.persistence.Entity;

@Entity
public class RegularRegistration extends Registration {

    RegularRegistration() {
    }

    RegularRegistration(User user, Lesson lesson) {
        super(user, lesson);
    }

    @Override
    public boolean isValid(SportModality sportModality, Lesson lesson) {
        return lesson.getSportModality().equals(sportModality);
    }

    @Override
    public double getCost() {
        return getLesson().getSportModality().getCostPerHour() * getLesson().getDuration() / 60 *
                getLesson().getDaysOfWeek().size() * 4;
    }

}
