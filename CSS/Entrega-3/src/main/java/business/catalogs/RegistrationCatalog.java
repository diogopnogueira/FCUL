package business.catalogs;

import java.util.HashMap;
import java.util.Map;

import business.Registration;
import business.RegularRegistration;
import business.SingleRegistration;

public class RegistrationCatalog {
	
	private Map<Integer,Registration> registrations;
	
	public RegistrationCatalog() {
		registrations = new HashMap<> ();
		loadRegistrations();
	}

	public Registration getRegistration(int registrationType) {
		return registrations.get(registrationType);
	}
	
	private void loadRegistrations() {
		registrations.put(1, new RegularRegistration());
		registrations.put(2, new SingleRegistration());
	}

	
}
