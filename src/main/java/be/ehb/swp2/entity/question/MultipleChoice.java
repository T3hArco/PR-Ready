package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.AnswerMediaType;
import be.ehb.swp2.entity.AnswerType;
import be.ehb.swp2.entity.Question;

/**
 * Created by Thomas on 29/10/2015.
 */

/**
 * @Todo implement this class
 */
public class MultipleChoice extends Question {
    public MultipleChoice(String title, String text, AnswerType answerType, AnswerMediaType answerMediaType, String answer) {
        super(title, text, answerType, answerMediaType, 1);
    }

    public boolean solve() {
        return false;
    }
}