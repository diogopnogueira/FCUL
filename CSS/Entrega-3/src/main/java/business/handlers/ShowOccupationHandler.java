package business.handlers;

import business.Facility;
import business.Lesson;
import business.Occupation;
import business.catalogs.FacilityCatalog;
import facade.exceptions.ApplicationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShowOccupationHandler {

	private FacilityCatalog facilityCatalog;

	public ShowOccupationHandler(FacilityCatalog facilityCatalog) {
		this.facilityCatalog = facilityCatalog;
	}

	public void showOccupation(String facilityName, int day, int month, int year) throws ApplicationException {
		System.out.println("-----------------UC4: Show Occupation-----------------");
		LocalDate date = LocalDate.of(year, month, day);
		Facility facility = validateFacility(facilityName);
		List<Occupation> occupations = getOccupations(facility.getOccupations(), date);
		printOccupations(occupations, date);
		System.out.println("-----------------End of UC4-----------------\n\n");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////// 


	private void printOccupations(List<Occupation> occupations, LocalDate date) {
		System.out.println("Occupations on " + date + ":");
		if (occupations.isEmpty())
			System.out.println("There are no occupations on that date!");
		for (Occupation occupation : occupations)
			System.out.println(occupation.toString());
	}

	private Facility validateFacility(String facilityName) throws ApplicationException {
		Facility facility = facilityCatalog.getFacilityByName(facilityName);
		if (facility == null)
			throw new ApplicationException("Invalid facility! Facility does not exist!");
		return facility;
	}

	private List<Occupation> getOccupations(List<Occupation> occupations, LocalDate date) {
		List<Occupation> result = new ArrayList<>();
		for (Occupation occupation : occupations) {
			if (dateIsBetween(date, occupation.getStartDate(), occupation.getEndDate()) &&
					occupation.getLesson().getDaysOfWeek().contains(date.getDayOfWeek()))
				result.add(occupation);
		}
		result.sort(Comparator.comparing(occupation -> occupation.getLesson().getStartHour()));
		return result;
	}

	private boolean dateIsBetween(LocalDate dateToCompare, LocalDate startDate, LocalDate endDate) {
		return dateToCompare.isAfter(startDate) && dateToCompare.isBefore(endDate);
	}

}
