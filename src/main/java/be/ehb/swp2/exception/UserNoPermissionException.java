package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 02/11/15.
 */
public class UserNoPermissionException extends Exception {
    public UserNoPermissionException() {
        super("The user does not have permission to access this object.");
    }
}
