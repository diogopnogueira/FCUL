package business.sportModality;

import facade.exceptions.InvalidSportModalityDurationException;
import facade.exceptions.SportModalityNotFoundException;

public class SportModalityValidator {
	
	private SportModalityValidator() {
	}
	
	public static void modalityExists(SportModality sportModality) throws SportModalityNotFoundException {
		if (sportModality == null)
			throw new SportModalityNotFoundException("Sport modality does not exist");
	}

	public static void validateDuration(int duration, SportModality sportModality) throws InvalidSportModalityDurationException {
		if (duration < sportModality.getMinDuration())
			throw new InvalidSportModalityDurationException("Invalid duration! Must be greater or equal than sport"
					+ " modality minimum duration");
	}

}
