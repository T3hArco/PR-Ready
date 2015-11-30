package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 02/11/15.
 */
public class DuplicateQuizException extends Exception {
    public DuplicateQuizException() {
        super("Duplicate quiz found!");
    }
}
