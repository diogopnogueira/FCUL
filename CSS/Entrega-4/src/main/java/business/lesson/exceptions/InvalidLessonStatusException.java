package business.lesson.exceptions;

public class InvalidLessonStatusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4842908022043622047L;

    public InvalidLessonStatusException(String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping a lower level exception.
     *
     * @param message The error message
     * @param e       The wrapped exception.
     */
    public InvalidLessonStatusException(String message, Exception e) {
        super(message, e);
    }

}
