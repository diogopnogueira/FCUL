package business.handlers;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.catalogs.FacilityCatalog;
import business.facility.Facility;
import business.facility.FacilityValidator;
import business.occupation.Occupation;
import facade.dto.OccupationDTO;
import facade.exceptions.ApplicationException;

@Stateless
public class ShowOccupationHandler {

	@EJB
	private FacilityCatalog facilityCatalog;

	public List<OccupationDTO> showOccupation(String facilityName, int day, int month, int year)
			throws ApplicationException {
		Facility facility = facilityCatalog.getFacilityByName(facilityName);
		FacilityValidator.facilityExists(facility);
		LocalDate date = LocalDate.of(year, month, day);
		try {
			List<Occupation> ocupations = facility.getOccupationsOnDate(date);
			return ocupations.stream()
					.map(o -> new OccupationDTO(o.getLesson().getStartHour().toString(),
							o.getLesson().getEndHour().toString(), o.getLesson().getName()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ApplicationException("Error fetching occupations!", e);
		}
	}

}
