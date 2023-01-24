package presentation.web.model;

import java.util.Collection;

import facade.dto.OccupationDTO;
import facade.remote.FacilityServiceRemote;

public class ShowOccupationModel extends Model {
	private String facilityName;
	private String day;
	private String month;
	private String year;
	private Iterable<OccupationDTO> occupations;
	private FacilityServiceRemote facilityService;

	public void setFacilityService(FacilityServiceRemote facilityService) {
		this.facilityService = facilityService;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getMonth() {
		return month;
	}

	public String getYear() {
		return year;
	}

	public String getDay() {
		return day;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String setYear(String year) {
		return this.year = year;
	}

	public String setMonth(String month) {
		return this.month = month;
	}

	public String setDay(String day) {
		return this.day = day;
	}

	public void setOccupation(Collection<OccupationDTO> occupations) {
		this.occupations = occupations;
	}

	public Iterable<OccupationDTO> getOccupations() {
		return occupations;
	}

	// public void clearFields() {
	// sportModalityName = lessonName = "";
	// registrationType = userId = "0";
	// }

}
