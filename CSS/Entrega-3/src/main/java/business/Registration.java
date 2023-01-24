package business;

public abstract class Registration {

	private User user;
	private Lesson lesson;

	public abstract double getCost();

	public abstract boolean isValid(SportModality sportModality, Lesson lesson);

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
