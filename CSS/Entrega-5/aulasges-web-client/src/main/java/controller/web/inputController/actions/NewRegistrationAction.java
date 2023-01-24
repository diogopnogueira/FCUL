package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.remote.RegistrationServiceRemote;
import presentation.web.model.RegisterLessonModel;

/***
 * Handles the novo cliente event**
 * 
 * @author fmartins
 *
 */
@Stateless
public class NewRegistrationAction extends Action {

	@EJB
	private RegistrationServiceRemote registrationService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RegisterLessonModel model = new RegisterLessonModel();
		model.setRegistrationService(registrationService);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/registerLesson/chooseSportAndRegistration.jsp").forward(request, response);
	}

}
