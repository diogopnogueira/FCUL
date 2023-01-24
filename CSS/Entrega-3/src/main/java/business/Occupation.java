package business;

import java.time.LocalDate;

public class Occupation {

	private LocalDate startDate;
	private LocalDate endDate;
	private Lesson lesson;

	public Occupation(LocalDate startDate, LocalDate endDate, Lesson lesson) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.lesson = lesson;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public String toString(){
		return "Lesson: " + lesson.getName() + "\n" +
				"Start hour: " + lesson.getStartHour() + "\n" +
				"End hour: " + lesson.getEndHour() + "\n";
	}

}
