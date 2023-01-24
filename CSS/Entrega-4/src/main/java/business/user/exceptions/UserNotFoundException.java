package business.user.exceptions;

public class UserNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -8436873803850686560L;

    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping a lower level exception.
     *
     * @param message The error message
     * @param e       The wrapped exception.
     */
    public UserNotFoundException(String message, Exception e) {
        super(message, e);
    }


}
