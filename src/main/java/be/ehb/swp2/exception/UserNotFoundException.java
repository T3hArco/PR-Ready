package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 05/11/15.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("The user could not be found ");
    }
}
