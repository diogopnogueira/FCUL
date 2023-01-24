package business.registration.exceptions;

public class InvalidRegistrationTypeException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 8800932193827682670L;

    public InvalidRegistrationTypeException(String message) {
        super(message);
    }


    /**
     * Creates an exception wrapping a lower level exception.
     *
     * @param message The error message
     * @param e       The wrapped exception.
     */
    public InvalidRegistrationTypeException(String message, Exception e) {
        super(message, e);
    }

}
