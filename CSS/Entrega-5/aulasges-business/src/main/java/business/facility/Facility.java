package business.facility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.lesson.Lesson;
import business.occupation.Occupation;
import business.sportModality.SportModality;

@Entity
@NamedQueries({ @NamedQuery(name = Facility.GET_ALL, query = "SELECT f FROM Facility f"),
		@NamedQuery(name = Facility.FIND_BY_NAME, query = "SELECT f FROM Facility f WHERE f.name = :"
				+ Facility.FACILITY_NAME), })
public class Facility {

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int maxCapacity;

	@Enumerated(EnumType.STRING)
	private FacilityType facilityType;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "FACILITY_ID")
	private List<Occupation> occupations;

	@ManyToMany
	private List<SportModality> sportModalities;

	public static final String GET_ALL = "Facility.getAll";
	public static final String FIND_BY_NAME = "Facility.findByName";
	public static final String FACILITY_NAME = "name";

	Facility() {
	}

	public Facility(String name, int maxCapacity, FacilityType facilityType, List<SportModality> sportModalities) {
		this.name = name;
		this.maxCapacity = maxCapacity;
		this.facilityType = facilityType;
		this.occupations = new ArrayList<>();
		this.sportModalities = sportModalities;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public FacilityType getFacilityType() {
		return facilityType;
	}

	public List<Occupation> getOccupations() {
		return occupations;
	}

	public void addOccupation(Occupation occupation) {
		occupations.add(occupation);
	}

	public boolean allowsSportModality(SportModality sportModality) {
		for (SportModality modality : sportModalities)
			if (modality.getName().equals(sportModality.getName()))
				return true;
		return false;
	}

	public boolean isAvailable(Lesson lesson) {
		for (Occupation occupation : occupations) {
			boolean checkDaysOfWeek = !Collections.disjoint(occupation.getLesson().getDaysOfWeek(),
					lesson.getDaysOfWeek());
			boolean checkLessonStartHour = hourIsBetween(lesson.getStartHour(), occupation.getLesson().getStartHour(),
					occupation.getLesson().getEndHour());
			boolean checkLessonEndHour = hourIsBetween(lesson.getEndHour(), occupation.getLesson().getStartHour(),
					occupation.getLesson().getEndHour());
			if (checkDaysOfWeek && (checkLessonStartHour || checkLessonEndHour))
				return false;
		}
		return true;
	}

	public List<Occupation> getOccupationsOnDate(LocalDate date) {
		List<Occupation> result = new ArrayList<>();
		for (Occupation occupation : occupations) {
			if (dateIsBetween(date, occupation.getStartDate(), occupation.getEndDate())
					&& occupation.getLesson().getDaysOfWeek().contains(date.getDayOfWeek()))
				result.add(occupation);
		}
		result.sort(Comparator.comparing(occupation -> occupation.getLesson().getStartHour()));
		return result;
	}

	private boolean dateIsBetween(LocalDate dateToCompare, LocalDate startDate, LocalDate endDate) {
		return dateToCompare.isAfter(startDate) && dateToCompare.isBefore(endDate);
	}

	private boolean hourIsBetween(LocalTime dateToCompare, LocalTime startDate, LocalTime endDate) {
		return dateToCompare.isAfter(startDate) && dateToCompare.isBefore(endDate);
	}

}
