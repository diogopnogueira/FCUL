package business.occupation;

import java.time.LocalDate;

import business.CurrentDate;
import facade.exceptions.InvalidOccupationDateException;

public class OccupationValidator {

	private OccupationValidator() {
	}

	public static void validateOccupationDate(LocalDate startDate, LocalDate endDate)
			throws InvalidOccupationDateException {
		if (CurrentDate.getCurrentDate().toLocalDate().isAfter(startDate)
				|| CurrentDate.getCurrentDate().toLocalDate().isAfter(endDate) || startDate.isAfter(endDate))
			throw new InvalidOccupationDateException("Invalid date! Date cannot be in the past");
	}

}
