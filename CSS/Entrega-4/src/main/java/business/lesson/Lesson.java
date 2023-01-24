package business.lesson;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import business.sportModality.SportModality;
import business.facility.Facility;
import business.registration.Registration;
import business.registration.RegularRegistration;
import business.converter.LocalTimeAttributeConverter;

@Entity
@NamedQueries({
	@NamedQuery(name = Lesson.FIND_BY_NAME, query = "SELECT l FROM Lesson l WHERE l.name = :" +
			Lesson.LESSON_NAME),
	@NamedQuery(name = Lesson.FIND_ACTIVE_BY_SPORTMODALITY, query = "SELECT l FROM Lesson l WHERE l.status = :" +
			Lesson.LESSON_STATUS + " AND l.sportModality = :" + Lesson.LESSON_SPORTMODALITY + " ORDER BY l.startHour")
})
public class Lesson {

	public static final String FIND_BY_NAME = "Lesson.findByName";
	public static final String FIND_ACTIVE_BY_SPORTMODALITY = "Lesson.findActiveBySportModality";

	public static final String LESSON_NAME = "name";
	public static final String LESSON_SPORTMODALITY = "sportModality";
	public static final String LESSON_STATUS = "status";


	@Id
	@GeneratedValue
	private int id;

	@Column(unique = true, nullable=false)
	private String name;

	@OneToOne
	private SportModality sportModality;

	@ManyToOne
	private Facility facility;

	@ElementCollection(fetch = FetchType.LAZY)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "LESSON_DAYS_OF_WEEK")
	@Column(name = "Day_of_Week")
	private List<DayOfWeek> daysOfWeek;

	@Column(nullable=false)
	@Convert(converter = LocalTimeAttributeConverter.class)
	private LocalTime startHour;

	@Column(nullable=false)
	@Convert(converter = LocalTimeAttributeConverter.class)
	private LocalTime endHour;

	@Column(nullable=false)
	private int duration;

	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private LessonStatus status;

	@Column(nullable=false)
	private int maxParticipants;

	@Column(nullable=false)
	private int vacancies;

	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
	private List<Registration> registrations;

	Lesson() {
	}

	public Lesson(String name, SportModality sportModality, String[] daysOfWeek,
			LocalTime startHour, int duration) {
		this.name = name;
		this.sportModality = sportModality;
		this.daysOfWeek = getDaysOfWeek(daysOfWeek);
		this.startHour = startHour;
		this.duration = duration;
		endHour = startHour.plusMinutes(duration);
		status = LessonStatus.INACTIVE;
		registrations = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public SportModality getSportModality() {
		return sportModality;
	}

	public Facility getFacility() {
		return facility;
	}

	public List<DayOfWeek> getDaysOfWeek() {
		return daysOfWeek;
	}

	public LocalTime getStartHour() {
		return startHour;
	}

	public LocalTime getEndHour() {
		return endHour;
	}

	public int getDuration() {
		return duration;
	}

	public int getMaxParticipants() {
		return maxParticipants;
	}

	public int getVacancies() {
		return vacancies;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public void setStatus(LessonStatus status) {
		this.status = status;
	}

	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}

	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
	}

	public boolean isActive() {
		return status == LessonStatus.ACTIVE;
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public int getNumberOfRegularRegistrations() {
		int regularRegistrationsNumber = 0;
		for (Registration registration : registrations) {
			if (registration instanceof RegularRegistration)
				regularRegistrationsNumber++;
		}
		return regularRegistrationsNumber;
	}

	public String toString() {
		String result = "Lesson name: " + name + "\n" +
				"Start hour: " + startHour.getHour() + ":" + startHour.getMinute() + "\n" +
				"End hour: " + endHour.getHour() + ":" + endHour.getMinute() + "\n" +
				"Days of Week: " + daysOfWeekToString() + "\n" +
				"Facility: " + facility.getName() + "\n";
		if (vacancies == 0)
			result += "Vacancies: " + (vacancies + 1) + "\n";
		else {
			result += "Vacancies: " + vacancies + "\n";
		}
		return result;

	}
	
    private List<DayOfWeek> getDaysOfWeek(String[] daysOfWeek) {
        List<DayOfWeek> result = new ArrayList<>();
        for (String day : daysOfWeek)
            result.add(DayOfWeek.valueOf(day));
        return result;
    }

	private String daysOfWeekToString() {
		StringBuilder result = new StringBuilder();
		for (DayOfWeek dayOfWeek : daysOfWeek)
			result.append(dayOfWeek.name()).append(" ");
		return result.toString();
	}

	public void activate(Facility facility, int maxParticipants) {
		setFacility(facility);
		setMaxParticipants(maxParticipants);
		setVacancies(maxParticipants);
		setStatus(LessonStatus.ACTIVE);
	}

	public void decrementVacancies() {
		if (vacancies > 0) 
			vacancies--;
	}


}
