package business;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userId;
    private String name;
    private int nif;
    private List<Registration> registrations;

    public User(int userId, String name, int nif) {
        this.userId = userId;
        this.name = name;
        this.nif = nif;
        registrations = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getNif() {
        return nif;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }


    public void addRegistration(Registration registration) {
        registrations.add(registration);
    }

}
