package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 05/11/15.
 */
public class TokenNotFoundException extends Exception {
    public TokenNotFoundException() {
        super("The user token was not found. It must be invalid");
    }
}
