package business.lesson.exceptions;

public class InvalidLessonNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7952445986780575895L;
	
    public InvalidLessonNameException(String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping a lower level exception.
     *
     * @param message The error message
     * @param e       The wrapped exception.
     */
    public InvalidLessonNameException(String message, Exception e) {
        super(message, e);
    }


}
