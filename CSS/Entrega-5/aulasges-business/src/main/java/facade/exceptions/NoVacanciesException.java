package facade.exceptions;

public class NoVacanciesException extends ApplicationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 2866766174594274555L;

	public NoVacanciesException(String message) {
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
	public NoVacanciesException(String message, Exception e) {
		super(message, e);
	}

}
