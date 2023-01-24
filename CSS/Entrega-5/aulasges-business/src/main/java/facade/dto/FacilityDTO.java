package facade.dto;

import java.io.Serializable;

public class FacilityDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 86990295005867199L;
	private int id;
	private String name;

	public FacilityDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
