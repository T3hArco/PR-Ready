package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 02/11/15.
 */
public class DuplicateUserException extends Exception {
    public DuplicateUserException() {
        super("Attepted to create duplicate user");
    }
}
