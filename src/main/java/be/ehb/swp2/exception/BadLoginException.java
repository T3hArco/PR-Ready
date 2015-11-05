package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 02/11/15.
 */
public class BadLoginException extends Exception {
    public BadLoginException() {
        super("The provided credentials are invalid.");
    }
}
