package business.handlers;

import business.catalogs.FacilityCatalog;
import business.facility.Facility;
import business.facility.FacilityValidator;
import facade.exceptions.ApplicationException;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ShowOccupationHandler {

	private EntityManagerFactory entityManagerFactory;

	public ShowOccupationHandler(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public String showOccupation(String facilityName, int day, int month, int year) throws ApplicationException {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		FacilityCatalog facilityCatalog = new FacilityCatalog(entityManager);
		try {

			entityManager.getTransaction().begin();
			Facility facility = facilityCatalog.getFacilityByName(facilityName);
			FacilityValidator.facilityExists(facility);
			LocalDate date = LocalDate.of(year, month, day);
			entityManager.getTransaction().commit();
			return facility.printOccupationsOnDate(date);

		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new ApplicationException("Error fetching occupations!", e);
		} finally {
			entityManager.close();
		}

	}

}
