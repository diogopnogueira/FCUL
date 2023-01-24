package facade.remote;

import java.util.Collection;

import javax.ejb.Remote;

import facade.dto.ActiveLessonDTO;
import facade.dto.RegistrationDTO;
import facade.dto.SportModalityDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface RegistrationServiceRemote {

	public Collection<SportModalityDTO> newRegistration() throws ApplicationException;

	public Collection<ActiveLessonDTO> chooseSportAndRegistrationType(String sportModalityName, String registrationType)
			throws ApplicationException;

	public void chooseUserId(int userId) throws ApplicationException;

	public RegistrationDTO chooseLesson(String lessonName) throws ApplicationException;
}
