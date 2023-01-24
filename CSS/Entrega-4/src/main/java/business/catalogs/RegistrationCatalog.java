package business.catalogs;

import javax.persistence.EntityManager;

import business.registration.Registration;

public class RegistrationCatalog {

    private EntityManager entityManager;

    public RegistrationCatalog(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persistRegistration(Registration registration) {
        entityManager.persist(registration);
    }

}
