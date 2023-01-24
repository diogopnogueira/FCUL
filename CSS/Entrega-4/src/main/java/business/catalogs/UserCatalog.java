package business.catalogs;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import business.user.User;
import business.user.exceptions.UserNotFoundException;

public class UserCatalog {

    private EntityManager entityManager;

    public UserCatalog(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
