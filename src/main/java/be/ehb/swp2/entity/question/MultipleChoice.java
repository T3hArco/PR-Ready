package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuestionType;

/**
 * Created by Thomas on 29/10/2015.
 */

/**
 * @Todo implement this class
 */
public class MultipleChoice extends Question {
    public MultipleChoice(String title, String text, QuestionType questionType, int questionExtraId, String answer) {
        super(title, text, questionType, questionExtraId);
    }

    public boolean solve() {
        return false;
    }
}