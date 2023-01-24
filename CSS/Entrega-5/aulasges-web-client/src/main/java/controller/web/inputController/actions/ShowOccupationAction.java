package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.exceptions.FacilityNotFoundException;
import facade.remote.FacilityServiceRemote;
import presentation.web.model.ShowOccupationModel;

@Stateless
public class ShowOccupationAction extends Action {
	@EJB
	private FacilityServiceRemote facilityService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ShowOccupationModel model = createHelper(request);
		request.setAttribute("model", model);

		if (validInput(model)) {
			try {
				model.setOccupation(facilityService.showOccupation(model.getFacilityName(), intValue(model.getDay()),
						intValue(model.getMonth()), intValue(model.getYear())));
				model.addMessage("Occupation successfully showed.");
			} catch (FacilityNotFoundException e) {
				model.addMessage("Error on facility name: " + e.getMessage());
			} catch (ApplicationException e) {
				model.addMessage("Error showing Occupation: " + e.getMessage());
			}
		} else
			model.addMessage("Error validating Occupation data");
		request.getRequestDispatcher("/showOccupation/showOccupation.jsp").forward(request, response);
	}

	private ShowOccupationModel createHelper(HttpServletRequest request) {
		ShowOccupationModel model = new ShowOccupationModel();
		model.setFacilityService(facilityService);
		model.setFacilityName(request.getParameter("facilityName"));
		model.setYear(request.getParameter("year"));
		model.setDay(request.getParameter("day"));
		model.setMonth(request.getParameter("month"));

		return model;
	}

	private boolean validInput(ShowOccupationModel model) {
		boolean result = isFilled(model, model.getFacilityName(), "Facility name must be filled.");

		result &= isFilled(model, model.getDay(), "Day must be filled.")
				&& isInt(model, model.getDay(), "Day contains invalid characters");

		result &= isFilled(model, model.getMonth(), "Month must be filled.")
				&& isInt(model, model.getMonth(), "Month contains invalid characters");

		result &= isFilled(model, model.getYear(), "Year must be filled.")
				&& isInt(model, model.getYear(), "Year contains invalid characters");

		return result;
	}

}
