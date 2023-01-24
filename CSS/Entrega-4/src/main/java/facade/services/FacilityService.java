package facade.services;

import business.facility.exceptions.FacilityNotFoundException;
import business.handlers.ShowOccupationHandler;
import facade.exceptions.ApplicationException;

public class FacilityService {

    private ShowOccupationHandler showOccupationHandler;

    public FacilityService(ShowOccupationHandler showOccupationHandler) {
        this.showOccupationHandler = showOccupationHandler;
    }

    public String showOccupation(String facilityName, int day, int month, int year) throws ApplicationException, FacilityNotFoundException {
        return showOccupationHandler.showOccupation(facilityName, day, month, year);
    }
}
