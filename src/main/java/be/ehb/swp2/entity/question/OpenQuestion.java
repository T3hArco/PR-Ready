package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.AnswerMediaType;
import be.ehb.swp2.entity.AnswerType;
import be.ehb.swp2.entity.Question;

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
    private AnswerType answerType;

    public OpenQuestion(String title, String text, AnswerType answerType, AnswerMediaType answerMediaType, ArrayList<String> answers) {
        super(title, text, answerType, answerMediaType, 1, 1);
        this.answers = answers;
        this.answerType = AnswerType.OPEN;
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
