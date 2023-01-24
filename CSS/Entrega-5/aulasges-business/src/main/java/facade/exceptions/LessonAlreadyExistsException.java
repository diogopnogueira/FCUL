package facade.exceptions;

public class LessonAlreadyExistsException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 250585564152061647L;

	public LessonAlreadyExistsException(String message) {
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
	public LessonAlreadyExistsException(String message, Exception e) {
		super(message, e);
	}
}
