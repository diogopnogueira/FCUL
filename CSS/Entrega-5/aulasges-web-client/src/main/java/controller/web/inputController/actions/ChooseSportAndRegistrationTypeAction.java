package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.exceptions.RegistrationTypeNotFoundException;
import facade.exceptions.SportModalityNotFoundException;
import facade.remote.RegistrationServiceRemote;
import presentation.web.model.RegisterLessonModel;

@Stateless
public class ChooseSportAndRegistrationTypeAction extends Action {

	@EJB
	private RegistrationServiceRemote registrationService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RegisterLessonModel model = createHelper(request);
		request.setAttribute("model", model);

		if (validInput(model)) {
			try {
				model.setActiveLessons(registrationService.chooseSportAndRegistrationType(model.getSportModalityName(),
						model.getRegistrationType()));
				model.addMessage("Sport modality and registration type are valid");
			} catch (SportModalityNotFoundException e) {
				model.addMessage("Error selecting sport modality: " + e.getMessage());
			} catch (RegistrationTypeNotFoundException e) {
				model.addMessage("Error selecting registration type: " + e.getMessage());
			} catch (ApplicationException e) {
				model.addMessage("Error validating data: " + e.getMessage());
			}
		} else
			model.addMessage("Error validating data");
		model.clearFields();
		request.getRequestDispatcher("/registerLesson/chooseLesson.jsp").forward(request, response);
	}

	private boolean validInput(RegisterLessonModel model) {

		boolean result = isFilled(model, model.getSportModalityName(), "Sport modality name must be filled.");

		result &= isFilled(model, model.getRegistrationType(), "Registration type must be filled");

		return result;
	}

	private RegisterLessonModel createHelper(HttpServletRequest request) {
		RegisterLessonModel model = new RegisterLessonModel();
		model.setRegistrationService(registrationService);
		model.setSportModalityName(request.getParameter("sportModalityName"));
		model.setRegistrationType(request.getParameter("registrationType"));

		return model;
	}
}
