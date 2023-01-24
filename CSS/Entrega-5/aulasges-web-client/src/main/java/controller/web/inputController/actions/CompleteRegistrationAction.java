package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.exceptions.InvalidLessonSportModalityException;
import facade.exceptions.InvalidLessonStatusException;
import facade.exceptions.InvalidRegistrationTypeException;
import facade.exceptions.InvalidUserRegistrationException;
import facade.exceptions.LessonNotAllowedOnRegistrationException;
import facade.exceptions.LessonNotFoundException;
import facade.exceptions.NoVacanciesException;
import facade.exceptions.UserNotFoundException;
import facade.remote.RegistrationServiceRemote;
import presentation.web.model.RegisterLessonModel;

@Stateless
public class CompleteRegistrationAction extends Action {

	@EJB
	private RegistrationServiceRemote registrationService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RegisterLessonModel model = createHelper(request);
		request.setAttribute("model", model);

		if (validInput(model)) {
			try {
				registrationService.chooseUserId(intValue(model.getUserId()));
				model.setCost(String.valueOf(registrationService.chooseLesson(model.getLessonName()).getCost()));
				model.clearFields();
				model.addMessage("Registration succeded!");
			} catch (UserNotFoundException | InvalidUserRegistrationException e) {
				model.addMessage("Error choosing user id: " + e.getMessage());
			} catch (LessonNotFoundException | NoVacanciesException | InvalidLessonStatusException
					| InvalidLessonSportModalityException e) {
				model.addMessage("Error choosing lesson: " + e.getMessage());
			} catch (InvalidRegistrationTypeException | LessonNotAllowedOnRegistrationException e) {
				model.addMessage("Error on registration: " + e.getMessage());
			} catch (ApplicationException e) {
				model.addMessage("Registration error: " + e.getMessage());
			}
		} else
			model.addMessage("Registration Error");

		request.getRequestDispatcher("/registerLesson/chooseLesson.jsp").forward(request, response);
	}

	private boolean validInput(RegisterLessonModel model) {

		boolean result = isFilled(model, model.getUserId(), "User id must be filled.")
				&& isInt(model, model.getUserId(), "User id contains invalid characters");

		result &= isFilled(model, model.getLessonName(), "Lesson name must be filled");

		return result;
	}

	private RegisterLessonModel createHelper(HttpServletRequest request) {
		RegisterLessonModel model = new RegisterLessonModel();
		model.setUserId(request.getParameter("userId"));
		model.setLessonName(request.getParameter("lessonName"));
		return model;
	}
}
