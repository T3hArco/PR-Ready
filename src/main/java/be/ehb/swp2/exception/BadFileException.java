package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class BadFileException extends Exception {

    /**
     * This function will throw a bad file error with a specific error
     *
     * @param specificError specific error text
     */
    public BadFileException(String specificError) {
        super(specificError);
    }
}
