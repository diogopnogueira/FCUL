package facade.exceptions;

public class RegistrationTypeNotFoundException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5039346773957375219L;

	public RegistrationTypeNotFoundException(String message) {
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
	public RegistrationTypeNotFoundException(String message, Exception e) {
		super(message, e);
	}
}
