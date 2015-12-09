package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuestionType;

import java.util.ArrayList;

/**
 * Created by Thomas on 29/10/2015.
 */

/**
 * @Todo implement this class
 */
public class MultipleChoice extends Question {
    private ArrayList<String> answers = new ArrayList<String>();
    private String solution;
    QuestionType questionType = QuestionType.MULTIPLE_CHOICE;
    public MultipleChoice(String title, String text, Integer questionExtraId, ArrayList<String> answers, String solution){
        super(title, text, null, questionExtraId);
        this.answers = answers;
        this.solution = solution;
    }
    public boolean solve() {
        return false;
    }
}