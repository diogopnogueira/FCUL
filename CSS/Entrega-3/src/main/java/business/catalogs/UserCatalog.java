package business.catalogs;

import java.util.LinkedList;
import java.util.List;

import business.User;

public class UserCatalog {

    private List<User> users;

    public UserCatalog() {
        users = new LinkedList<>();
        loadUsers();
    }

    public User getUserByUserId(int userId) {
        for (User user : users)
            if (user.getUserId() == userId)
                return user;
        return null;
    }

    public void updateUser(User user) {
        users.set(users.indexOf(user), user);
    }

    private void loadUsers() {
        users.add(new User(12345, "Diogo", 123456789));
        users.add(new User(11111, "Andre", 111111111));
        users.add(new User(22222, "Ana", 222222222));
        users.add(new User(33333, "Joao", 333333333));
        users.add(new User(44444, "Tiago", 444444444));
    }

}
