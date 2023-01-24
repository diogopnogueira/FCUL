package business.catalogs;

import business.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FacilityCatalog {

    private List<Facility> facilities;

    public FacilityCatalog() {
        this.facilities = new LinkedList<>();
        loadProducts();
    }

    public List<Facility> getAllFacilities() {
        return facilities;
    }

    public Facility getFacilityByName(String facilityName) {
        for (Facility facility : facilities)
            if (facility.getName().equals(facilityName))
                return facility;
        return null;
    }

    public void addOccupation(Facility facility, LocalDate startDate, LocalDate endDate, Lesson lesson) {
        facility.addOccupation(new Occupation(startDate, endDate, lesson));
        facilities.set(facilities.indexOf(facility), facility);
        System.out.println("Facility '" + facility.getName() + "' is now occupied from " + startDate + " until " + endDate);
    }

    private void loadProducts() {
        SportModalityCatalog sportModality = new SportModalityCatalog();
        List<SportModality> sportModalities1 = new ArrayList<>();
        sportModalities1.add(sportModality.getSportModalityByName("Ginasio"));

        List<SportModality> sportModalities2 = new ArrayList<>();
        sportModalities2.add(sportModality.getSportModalityByName("Ginasio"));

        List<SportModality> sportModalities3 = new ArrayList<>();
        sportModalities3.add(sportModality.getSportModalityByName("Natacao"));

        List<SportModality> sportModalities4 = new ArrayList<>();
        sportModalities4.add(sportModality.getSportModalityByName("Tenis"));

        facilities.add(new Facility("Sala de Bicicletas", 20, sportModalities1));
        facilities.add(new Facility("Estudio", 30, sportModalities2));
        facilities.add(new Facility("Piscina", 10, sportModalities3));
        facilities.add(new Facility("Campo de Tenis", 4, sportModalities4));
    }

}
