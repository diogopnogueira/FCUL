package facade.exceptions;

public class InvalidUserRegistrationException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 985818235099417882L;

	public InvalidUserRegistrationException(String message) {
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
	public InvalidUserRegistrationException(String message, Exception e) {
		super(message, e);
	}
}
