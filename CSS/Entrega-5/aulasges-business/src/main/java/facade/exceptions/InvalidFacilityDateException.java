package facade.exceptions;

public class InvalidFacilityDateException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1158533360554881939L;

	public InvalidFacilityDateException(String message) {
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
	public InvalidFacilityDateException(String message, Exception e) {
		super(message, e);
	}
}
