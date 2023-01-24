package business.registration;

import business.registration.exceptions.InvalidRegistrationTypeException;

public class RegistrationFactory {

	private RegistrationFactory(){
	}

	public static Registration createRegistration(int registrationType) throws InvalidRegistrationTypeException {
		Registration registration;
		switch (registrationType) {
		case 0:
			registration = new SingleRegistration();
			break;
		case 1:
			registration = new RegularRegistration();
			break;
		default:
			throw new InvalidRegistrationTypeException("Invalid registration type!");
		}
		return registration;
	}

}
