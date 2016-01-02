package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.AnswerMediaType;
import be.ehb.swp2.entity.AnswerType;
import be.ehb.swp2.entity.Question;

import java.util.ArrayList;

/**
 * Created by Thomas on 29/10/2015.
 */

/**
 * @Todo implement this class
 */
public class MultipleChoice extends Question {
    private ArrayList<String> answers = new ArrayList<String>();
    public MultipleChoice(String title, String text, AnswerType answerType, AnswerMediaType answerMediaType, String answer) {
        super(title, text, answerType, answerMediaType, 1);
        this.answers = answers;
    }

    public boolean solve() {
        return false;
    }
}