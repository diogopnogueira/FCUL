package facade.dto;

import java.io.Serializable;

public class OccupationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1577278712830485400L;
	private String endHour;
	private String startHour;
	private String lessonName;

	public OccupationDTO(String startHour, String endHour, String lesson) {
		this.startHour = startHour;
		this.endHour = endHour;
		this.lessonName = lesson;
	}

	public String getStartDate() {
		return startHour;
	}

	public String getEndDate() {
		return endHour;
	}

	public String getLesson() {
		return lessonName;
	}

	@Override
	public String toString() {
		return "Lesson: " + lessonName + "\n" + "Start hour: " + startHour + "\n" + "End hour: " + endHour + "\n";

	}
}
