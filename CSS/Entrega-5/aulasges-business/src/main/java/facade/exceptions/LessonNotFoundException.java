package facade.exceptions;

public class LessonNotFoundException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8321291028130760417L;

	public LessonNotFoundException(String message) {
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
	public LessonNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
