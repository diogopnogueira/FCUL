package business.catalogs;

import business.facility.exceptions.FacilityNotFoundException;
import business.facility.Facility;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class FacilityCatalog {

    private EntityManager entityManager;

    public FacilityCatalog(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Iterable<Facility> getAllFacilities() {
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

    public void persistFacility(Facility facility) {
        entityManager.persist(facility);
    }

}
