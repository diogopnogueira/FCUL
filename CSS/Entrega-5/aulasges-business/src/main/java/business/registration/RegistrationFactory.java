package business.registration;

import facade.exceptions.InvalidRegistrationTypeException;

public class RegistrationFactory {

	private RegistrationFactory() {
	}

	public static Registration createRegistration(String registrationType) throws InvalidRegistrationTypeException {
		Registration registration;
		switch (registrationType) {
		case "avulso":
			registration = new SingleRegistration();
			break;
		case "regular":
			registration = new RegularRegistration();
			break;
		default:
			throw new InvalidRegistrationTypeException("Invalid registration type!");
		}
		return registration;
	}

}
