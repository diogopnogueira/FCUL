package presentation.web.model;

import java.util.Collection;
import java.util.LinkedList;

import facade.dto.ActiveLessonDTO;
import facade.dto.SportModalityDTO;
import facade.exceptions.ApplicationException;
import facade.remote.RegistrationServiceRemote;

/***
 * Helper
 * 
 * class to assist in the response of novo cliente.*This
 * 
 * class is the response information expert.**
 * 
 * @author fmartins
 *
 */
public class RegisterLessonModel extends Model {

	private Iterable<SportModalityDTO> sportModalities;
	private String sportModalityName;
	private String registrationType;
	private String userId;
	private String lessonName;
	private Iterable<ActiveLessonDTO> activeLessons;
	private String cost;
	private RegistrationServiceRemote registrationService;

	public void setRegistrationService(RegistrationServiceRemote registrationService) {
		this.registrationService = registrationService;
	}

	public String getSportModalityName() {
		return sportModalityName;
	}

	public void setSportModalityName(String sportModalityName) {
		this.sportModalityName = sportModalityName;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLessonName() {
		return lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public void clearFields() {
		sportModalityName = lessonName = "";
		registrationType = userId = "0";
	}

	public Iterable<SportModalityDTO> getSportModalities() {
		try {
			return registrationService.newRegistration();
		} catch (ApplicationException e) {
			return new LinkedList<SportModalityDTO>();
		}
	}

	public Iterable<ActiveLessonDTO> getActiveLessons() {
		return activeLessons;
	}

	public void setActiveLessons(Collection<ActiveLessonDTO> activeLessons) {
		this.activeLessons = activeLessons;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;

	}

}
