package facade.dto;

import java.io.Serializable;

public class RegistrationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2161183569041666331L;
	private int id;
	private String userName;
	private String lessonName;
	private double cost;

	public RegistrationDTO(int id, String userName, String lessonName, double cost) {
		this.id = id;
		this.userName = userName;
		this.lessonName = lessonName;
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getLessonName() {
		return lessonName;
	}

	public double getCost() {
		return cost;
	}

}
