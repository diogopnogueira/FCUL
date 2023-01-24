package business.lesson.exceptions;

public class InvalidLessonSportModalityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1564899080571849410L;

    public InvalidLessonSportModalityException(String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping a lower level exception.
     *
     * @param message The error message
     * @param e       The wrapped exception.
     */
    public InvalidLessonSportModalityException(String message, Exception e) {
        super(message, e);
    }
}
