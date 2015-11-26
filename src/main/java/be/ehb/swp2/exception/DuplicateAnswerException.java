package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 02/11/15.
 */
public class DuplicateAnswerException extends Exception {
    public DuplicateAnswerException() {
        super("Duplicate question answer");
    }
}
