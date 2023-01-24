package facade.dto;

import java.io.Serializable;

public class ActiveLessonDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9017719297094073852L;
	private int id;
	private String name;
	private String daysOfWeek;
	private String startHour;
	private String endHour;
	private String facility;
	private int vacancies;

	public ActiveLessonDTO(int id, String name, String daysOfWeek, String startHour, String endHour, String facility,
			int vacancies) {
		this.id = id;
		this.name = name;
		this.daysOfWeek = daysOfWeek;
		this.startHour = startHour;
		this.endHour = endHour;
		this.facility = facility;
		this.vacancies = vacancies;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStartHour() {
		return startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public String getFacility() {
		return facility;
	}

	public int getVacancies() {
		return vacancies;
	}

	@Override
	public String toString() {
		String result = "Lesson name: " + name + "\n" + "Start hour: " + startHour + "\n" + "End hour: " + endHour
				+ "\n" + "Days of Week: " + daysOfWeek + "\n" + "Facility: " + facility + "\n";
		if (vacancies == 0)
			result += "Vacancies: " + (vacancies + 1) + "\n";
		else {
			result += "Vacancies: " + vacancies + "\n";
		}
		return result;

	}
}
