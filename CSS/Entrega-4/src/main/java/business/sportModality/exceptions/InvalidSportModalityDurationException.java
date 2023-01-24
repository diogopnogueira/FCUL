package business.sportModality.exceptions;

public class InvalidSportModalityDurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1043338335893172185L;
	
    public InvalidSportModalityDurationException(String message) {
        super(message);
    }


    /**
     * Creates an exception wrapping a lower level exception.
     *
     * @param message The error message
     * @param e       The wrapped exception.
     */
    public InvalidSportModalityDurationException(String message, Exception e) {
        super(message, e);
    }

}
