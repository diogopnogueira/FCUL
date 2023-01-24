package facade.exceptions;

public class LessonNotAllowedOnRegistrationException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1650376331860406344L;

	public LessonNotAllowedOnRegistrationException(String message) {
		super(message);
	}

	/**
	 * Creates an exception wrapping a lower level exception.
	 *
	 * @param message
	 *            The error message
	 * @param e
	 *            The wrapped exception.
	 */
	public LessonNotAllowedOnRegistrationException(String message, Exception e) {
		super(message, e);
	}
}
