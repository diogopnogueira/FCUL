package business.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.registration.Registration;

@Entity
@NamedQuery(name = User.FIND_BY_USER_ID, query = "SELECT u FROM User u WHERE u.userId = :" + User.USER_ID_NUMBER)
public class User {

	public static final String FIND_BY_USER_ID = "User.getByUserId";

	public static final String USER_ID_NUMBER = "userId";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false, unique = true)
	private int userId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private int nif;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Registration> registrations;

	User() {
	}

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

}
