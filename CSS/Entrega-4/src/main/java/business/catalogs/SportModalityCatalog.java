package business.catalogs;

import business.sportModality.SportModality;
import business.sportModality.exceptions.SportModalityNotFoundException;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class SportModalityCatalog {

    private EntityManager entityManager;

    public SportModalityCatalog(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public SportModality getSportModalityByName(String sportModalityName) throws SportModalityNotFoundException {
        try {
            TypedQuery<SportModality> query = entityManager.createNamedQuery(SportModality.FIND_BY_NAME, SportModality.class);
            query.setParameter(SportModality.SPORT_MODALITY_NAME, sportModalityName);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new SportModalityNotFoundException("Sport with name " + sportModalityName + " does not exist!", e);
        }
    }

    public Iterable<String> getAllSportModalities() {
        try {
            TypedQuery<String> query = entityManager.createNamedQuery(SportModality.GET_ALL, String.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
