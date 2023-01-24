package business;

public class SportModality {

	private String name;
	private int minDuration;
	private double costPerHour;

	public SportModality(String name, int minDuration, double costPerHour) {
		this.name = name;
		this.minDuration = minDuration;
		this.costPerHour = costPerHour;
	}

	public String getName() {
		return name;
	}

	public double getCostPerHour() {
		return costPerHour;
	}

	public int getMinDuration() {
		return minDuration;
	}

}
