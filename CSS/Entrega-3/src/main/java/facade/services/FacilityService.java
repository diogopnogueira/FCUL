package facade.services;

import business.handlers.ShowOccupationHandler;
import facade.exceptions.ApplicationException;

public class FacilityService {

    private ShowOccupationHandler showOccupationHandler;

    public FacilityService(ShowOccupationHandler showOccupationHandler) {
        this.showOccupationHandler = showOccupationHandler;
    }

    public void showOccupation(String facilityName, int day, int month, int year) throws ApplicationException {
        showOccupationHandler.showOccupation(facilityName, day, month, year);
    }
}
