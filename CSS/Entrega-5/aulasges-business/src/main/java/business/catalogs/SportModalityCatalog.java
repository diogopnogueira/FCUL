package business.catalogs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.sportModality.SportModality;
import facade.exceptions.SportModalityNotFoundException;

@Stateless
public class SportModalityCatalog {

	@PersistenceContext
	private EntityManager entityManager;

	public SportModality getSportModalityByName(String sportModalityName) throws SportModalityNotFoundException {
		try {
			TypedQuery<SportModality> query = entityManager.createNamedQuery(SportModality.FIND_BY_NAME,
					SportModality.class);
			query.setParameter(SportModality.SPORT_MODALITY_NAME, sportModalityName);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new SportModalityNotFoundException("Sport with name " + sportModalityName + " does not exist!", e);
		}
	}

	public List<SportModality> getAllSportModalities() {
		try {
			TypedQuery<SportModality> query = entityManager.createNamedQuery(SportModality.GET_ALL,
					SportModality.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}

}
