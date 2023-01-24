package business.catalogs;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.user.User;
import facade.exceptions.UserNotFoundException;

@Stateless
public class UserCatalog {

	@PersistenceContext
	private EntityManager entityManager;

	public User getUserByUserId(int userId) throws UserNotFoundException {
		try {
			TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_USER_ID, User.class);
			query.setParameter(User.USER_ID_NUMBER, userId);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new UserNotFoundException("User with user id " + userId + " does not exist!", e);
		}
	}

}
