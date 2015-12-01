package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 30/11/15.
 */
public class InternalErrorException extends Exception {
    public InternalErrorException() {
        super("An internal error occured");
    }
}
