package facade.exceptions;

public class InvalidFacilitySportModalityException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2541537947632809581L;

	public InvalidFacilitySportModalityException(String message) {
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
	public InvalidFacilitySportModalityException(String message, Exception e) {
		super(message, e);
	}

}
