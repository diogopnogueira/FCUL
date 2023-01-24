package facade.dto;

import java.io.Serializable;

public class SportModalityDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4953089143152907255L;
	private int id;
	private String name;
	private int minDuration;
	private double costPerHour;

	public SportModalityDTO(int id, String name, int minDuration, double costPerHour) {
		this.id = id;
		this.name = name;
		this.minDuration = minDuration;
		this.costPerHour = costPerHour;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMinDuration() {
		return minDuration;
	}

	public double getCostPerHour() {
		return costPerHour;
	}

}
