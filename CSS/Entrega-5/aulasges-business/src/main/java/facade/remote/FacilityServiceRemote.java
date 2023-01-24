package facade.remote;

import java.util.List;

import javax.ejb.Remote;

import facade.dto.OccupationDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.FacilityNotFoundException;

@Remote
public interface FacilityServiceRemote {

	public List<OccupationDTO> showOccupation(String facilityName, int day, int month, int year)
			throws ApplicationException, FacilityNotFoundException;

}
