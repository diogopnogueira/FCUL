package facade.services;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.RegisterLessonHandler;
import facade.dto.ActiveLessonDTO;
import facade.dto.RegistrationDTO;
import facade.dto.SportModalityDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.SportModalityNotFoundException;
import facade.exceptions.UserNotFoundException;
import facade.remote.RegistrationServiceRemote;

@Stateless
public class RegistrationService implements RegistrationServiceRemote {

	@EJB
	private RegisterLessonHandler registrationLessonHandler;

	@Override
	public Collection<SportModalityDTO> newRegistration() throws ApplicationException {
		return registrationLessonHandler.newRegistration();
	}

	@Override
	public Collection<ActiveLessonDTO> chooseSportAndRegistrationType(String sportModalityName, String registrationType)
			throws ApplicationException, SportModalityNotFoundException {
		return registrationLessonHandler.chooseSportAndRegistrationType(sportModalityName, registrationType);
	}

	@Override
	public void chooseUserId(int userId) throws ApplicationException, UserNotFoundException {
		registrationLessonHandler.chooseUserId(userId);
	}

	@Override
	public RegistrationDTO chooseLesson(String lessonName) throws ApplicationException, UserNotFoundException {
		return registrationLessonHandler.chooseLesson(lessonName);
	}

}
