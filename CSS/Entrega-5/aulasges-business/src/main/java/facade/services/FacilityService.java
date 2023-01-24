package facade.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.ShowOccupationHandler;
import facade.dto.OccupationDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.FacilityNotFoundException;
import facade.remote.FacilityServiceRemote;

@Stateless
public class FacilityService implements FacilityServiceRemote {
	@EJB
	private ShowOccupationHandler showOccupationHandler;

	@Override
	public List<OccupationDTO> showOccupation(String facilityName, int day, int month, int year)
			throws ApplicationException, FacilityNotFoundException {
		return showOccupationHandler.showOccupation(facilityName, day, month, year);
	}

}
