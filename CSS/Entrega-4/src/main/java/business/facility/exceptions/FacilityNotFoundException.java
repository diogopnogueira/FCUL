package business.facility.exceptions;

public class FacilityNotFoundException extends Exception {

    /**
     * Generated by Eclipse
     */
    private static final long serialVersionUID = -1679921359161647333L;

    public FacilityNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates an exception wrapping a lower level exception.
     *
     * @param message The error message
     * @param e       The wrapped exception.
     */
    public FacilityNotFoundException(String message, Exception e) {
        super(message, e);
    }

}


