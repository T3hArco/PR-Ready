package be.ehb.swp2.exception;

/**
 * Created by arnaudcoel on 02/11/15.
 */
public class QuizNotFoundException extends Exception {
    public QuizNotFoundException() {
        super("Quiz not found!");
    }
}
