package facade.exceptions;

public class InvalidOccupationDateException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7223258872591048719L;

	public InvalidOccupationDateException(String message) {
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
	public InvalidOccupationDateException(String message, Exception e) {
		super(message, e);
	}
}
