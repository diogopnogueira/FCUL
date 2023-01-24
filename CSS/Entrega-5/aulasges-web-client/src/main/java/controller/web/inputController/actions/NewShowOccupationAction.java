package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.remote.FacilityServiceRemote;
import presentation.web.model.ShowOccupationModel;

@Stateless
public class NewShowOccupationAction extends Action {

	@EJB
	private FacilityServiceRemote facilityService;;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShowOccupationModel model = new ShowOccupationModel();
		model.setFacilityService(facilityService);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/showOccupation/showOccupation.jsp").forward(request, response);
	}
}
