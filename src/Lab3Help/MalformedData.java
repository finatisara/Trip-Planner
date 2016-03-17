package Lab3Help;

/** Signals that malformed data has been encountered. */
public class MalformedData extends Exception {
    /** Constructs an exception with the given error message. */
    public MalformedData(String message) {
        super(message);
    }
}
