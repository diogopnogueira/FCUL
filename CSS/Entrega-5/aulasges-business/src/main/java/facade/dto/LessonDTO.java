package facade.dto;

import java.io.Serializable;

public class LessonDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3943292852110091680L;
	private int id;
	private String name;
	private String sportModality;
	private String[] daysOfWeek;
	private String startHour;
	private String endHour;
	private int duration;
	private String status;

	public LessonDTO(int id, String name, String sportModality, String[] daysOfWeek, String startHour, String endHour,
			int duration, String status) {
		this.id = id;
		this.name = name;
		this.sportModality = sportModality;
		this.daysOfWeek = daysOfWeek;
		this.startHour = startHour;
		this.endHour = endHour;
		this.duration = duration;
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSportModality() {
		return sportModality;
	}

	public String[] getDaysOfWeek() {
		return daysOfWeek;
	}

	public String getStartHour() {
		return startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public int getDuration() {
		return duration;
	}

	public String getStatus() {
		return status;
	}

}