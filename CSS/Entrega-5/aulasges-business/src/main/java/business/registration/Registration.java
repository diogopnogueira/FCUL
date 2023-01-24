package business.registration;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import business.lesson.Lesson;
import business.sportModality.SportModality;
import business.user.User;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "REGISTRATIONTYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Registration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Lesson lesson;

	@Version
	private int version;

	Registration() {
	}

	public Registration(User user, Lesson lesson) {
		this.user = user;
		this.lesson = lesson;
	}

	public abstract double getCost();

	public abstract boolean isValid(SportModality sportModality, Lesson lesson);

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

}
