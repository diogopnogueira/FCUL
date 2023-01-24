package business.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import business.catalogs.LessonCatalog;
import business.catalogs.RegistrationCatalog;
import business.catalogs.SportModalityCatalog;
import business.catalogs.UserCatalog;
import business.lesson.Lesson;
import business.lesson.LessonValidator;
import business.registration.Registration;
import business.registration.RegistrationFactory;
import business.registration.RegistrationValidator;
import business.sportModality.SportModality;
import business.sportModality.SportModalityValidator;
import business.user.User;
import business.user.UserValidator;
import facade.dto.ActiveLessonDTO;
import facade.dto.RegistrationDTO;
import facade.dto.SportModalityDTO;
import facade.exceptions.ApplicationException;

@Stateful
public class RegisterLessonHandler {

	@EJB
	private SportModalityCatalog sportsModalityCatalog;

	@EJB
	private LessonCatalog lessonCatalog;

	@EJB
	private UserCatalog userCatalog;

	@EJB
	private RegistrationCatalog registrationCatalog;

	private String currentRegistrationType;
	private SportModality currentSportModality;
	private User currentUser;

	public Collection<SportModalityDTO> newRegistration() throws ApplicationException {
		try {
			List<SportModality> entityList = sportsModalityCatalog.getAllSportModalities();
			return getSportModalities(entityList);
		} catch (Exception e) {
			throw new ApplicationException("Error fetching sport modalities!", e);
		}
	}

	public Collection<ActiveLessonDTO> chooseSportAndRegistrationType(String sportModalityName, String registrationType)
			throws ApplicationException {

		currentSportModality = sportsModalityCatalog.getSportModalityByName(sportModalityName);
		registrationType = registrationType.toLowerCase();
		SportModalityValidator.modalityExists(currentSportModality);
		RegistrationValidator.validateRegistrationType(registrationType);
		currentRegistrationType = registrationType;
		List<Lesson> activeLessons = lessonCatalog.getActiveLessonsBySportModality(currentSportModality);
		LessonValidator.checkActiveLessonsOnSportModality(activeLessons);
		try {
			return showActiveLessons(activeLessons);
		} catch (Exception e) {
			throw new ApplicationException("Error fetching active lessons!", e);
		}
	}

	public void chooseUserId(int userId) throws ApplicationException {
		User user = userCatalog.getUserByUserId(userId);
		UserValidator.userExists(user);
		currentUser = user;
	}

	public RegistrationDTO chooseLesson(String lessonName) throws ApplicationException {

		Lesson lesson = lessonCatalog.getLessonByName(lessonName);

		LessonValidator.lessonExists(lesson);
		LessonValidator.lessonHasSameSportModality(lesson, currentSportModality);
		LessonValidator.lessonIsActive(lesson);
		LessonValidator.lessonHasVacancies(currentRegistrationType, lesson);
		UserValidator.userHasAlreadyRegisteredOnLesson(lesson, currentUser);

		Registration registration = RegistrationFactory.createRegistration(currentRegistrationType);
		registration.setUser(currentUser);
		RegistrationValidator.registrationIsValidOnLesson(registration, lesson);

		try {
			lesson.decrementVacancies();
			registration.setLesson(lesson);
			Registration newRegistration = registrationCatalog.persistRegistration(registration);
			return new RegistrationDTO(newRegistration.getId(), newRegistration.getUser().getName(),
					newRegistration.getLesson().getName(), Math.round(newRegistration.getCost() * 100.0) / 100.0);
		} catch (Exception e) {
			throw new ApplicationException("Error creating registration!", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private List<ActiveLessonDTO> showActiveLessons(List<Lesson> activeLessons) {
		List<ActiveLessonDTO> result = new ArrayList<>();
		for (Lesson lesson : activeLessons) {
			if (currentRegistrationType.equals("avulso") && lesson.getVacancies() > 0)
				result.add(new ActiveLessonDTO(lesson.getId(), lesson.getName(),
						lesson.getDaysOfWeek().stream().map(Object::toString).collect(Collectors.joining(",")),
						lesson.getStartHour().toString(), lesson.getEndHour().toString(),
						lesson.getFacility().getName(), lesson.getVacancies()));
			if (currentRegistrationType.equals("regular")
					&& lesson.getNumberOfRegularRegistrations() < lesson.getMaxParticipants())
				result.add(new ActiveLessonDTO(lesson.getId(), lesson.getName(),
						lesson.getDaysOfWeek().stream().map(Object::toString).collect(Collectors.joining(",")),
						lesson.getStartHour().toString(), lesson.getEndHour().toString(),
						lesson.getFacility().getName(), lesson.getVacancies()));
		}
		return result;
	}

	private List<SportModalityDTO> getSportModalities(List<SportModality> entityList) {
		List<SportModalityDTO> result = new ArrayList<>();
		for (SportModality sportModalityEntity : entityList)
			result.add(new SportModalityDTO(sportModalityEntity.getId(), sportModalityEntity.getName(),
					sportModalityEntity.getMinDuration(), sportModalityEntity.getCostPerHour()));
		return result;
	}

}
