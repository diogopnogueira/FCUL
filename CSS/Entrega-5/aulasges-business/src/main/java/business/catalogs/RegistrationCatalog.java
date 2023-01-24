package business.catalogs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import business.registration.Registration;

@Stateless
public class RegistrationCatalog {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public Registration persistRegistration(Registration registration) {
		Registration newRegistration = registration;
		entityManager.persist(newRegistration);
		return newRegistration;
	}

}
