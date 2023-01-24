package business.facility;

import business.lesson.Lesson;
import facade.exceptions.FacilityNotFoundException;
import facade.exceptions.InvalidFacilityDateException;
import facade.exceptions.InvalidFacilityParticipantsException;
import facade.exceptions.InvalidFacilitySportModalityException;

public class FacilityValidator {
	
	private FacilityValidator() {
	}

	public static void facilityExists(Facility facility) throws FacilityNotFoundException {
		if (facility == null)
			throw new FacilityNotFoundException("Invalid facility! Facility does not exists");
	}

	public static void facilityAllowsSportModality(Facility facility, Lesson lesson) throws InvalidFacilitySportModalityException {
		if (!facility.allowsSportModality(lesson.getSportModality()))
			throw new InvalidFacilitySportModalityException("Facility does not allow this modality");
	}

	public static void facilityIsAvailable(Facility facility, Lesson lesson) throws InvalidFacilityDateException {
		if (!facility.isAvailable(lesson))
			throw new InvalidFacilityDateException("Invalid date! Facility is not available");
	}

	public static void validateMaxParticipantsOnFacility(Facility facility, int maxParticipants) throws InvalidFacilityParticipantsException {
		if (maxParticipants > facility.getMaxCapacity())
			throw new InvalidFacilityParticipantsException("Number of participants is greater than facility's max capacity!");
	}
}
