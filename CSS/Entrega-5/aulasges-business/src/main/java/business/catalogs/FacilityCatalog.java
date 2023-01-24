package business.catalogs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.facility.Facility;
import facade.exceptions.FacilityNotFoundException;

@Stateless
public class FacilityCatalog {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Facility> getAllFacilities() {
		try {
			TypedQuery<Facility> query = entityManager.createNamedQuery(Facility.GET_ALL, Facility.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public Iterable<String> getAllFacilityNames() {
		try {
			TypedQuery<String> query = entityManager.createNamedQuery(Facility.GET_ALL, String.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public Facility getFacilityByName(String facilityName) throws FacilityNotFoundException {
		try {
			TypedQuery<Facility> query = entityManager.createNamedQuery(Facility.FIND_BY_NAME, Facility.class);
			query.setParameter(Facility.FACILITY_NAME, facilityName);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new FacilityNotFoundException("Facility with name " + facilityName + " does not exist!", e);
		}
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void persistFacility(Facility facility) {
		entityManager.persist(facility);
	}

}
