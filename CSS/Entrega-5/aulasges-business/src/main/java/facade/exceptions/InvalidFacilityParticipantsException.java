package facade.exceptions;

public class InvalidFacilityParticipantsException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8456243123979291242L;

	public InvalidFacilityParticipantsException(String message) {
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
	public InvalidFacilityParticipantsException(String message, Exception e) {
		super(message, e);
	}
}
