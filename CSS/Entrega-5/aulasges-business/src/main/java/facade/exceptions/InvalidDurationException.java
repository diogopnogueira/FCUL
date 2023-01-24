package facade.exceptions;

public class InvalidDurationException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7796243687312055976L;

	public InvalidDurationException(String message) {
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
	public InvalidDurationException(String message, Exception e) {
		super(message, e);
	}

}
