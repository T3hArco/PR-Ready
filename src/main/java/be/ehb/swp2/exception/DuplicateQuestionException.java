package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 02/11/15.
 */
public class DuplicateQuestionException extends Exception {
    public DuplicateQuestionException() {
        super("Duplicate question");
    }
}
