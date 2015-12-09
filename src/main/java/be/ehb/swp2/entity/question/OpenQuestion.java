package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuestionType;

import java.util.ArrayList;

/**
 * Created by Thomas on 29/10/2015.
 */

/**
 * Class for an open question which allows any form of response in a string
 *
 * @Todo implement this further
 */
public class OpenQuestion extends Question {
    /**
     * The answer to the question
     */
    private ArrayList<String> answers;
    private QuestionType questionType;

    public OpenQuestion(String title, String text, QuestionType questionType, Integer questionExtraId, ArrayList<String> answers) {
        super(title, text, questionType, questionExtraId);
        this.answers = answers;
        this.questionType = QuestionType.OPEN;
    }

    /**
     * Adds an answer to the allowed answers.
     *
     * @param answer
     */
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    /**
     * Checks whether the answer is correct.
     *
     * @param answer
     * @return vailidity
     * @todo implement a better way to do this.
     */
    public boolean solve(String answer) {
        return answers.contains(answer);

    }
}
