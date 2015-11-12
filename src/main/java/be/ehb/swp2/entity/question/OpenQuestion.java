package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;

import java.util.ArrayList;

/**
 * Created by Thomas on 29/10/2015.
 */

/**
 * Class for an open question which allows any form of response in a string
 * @Todo implement this further
 */
public class OpenQuestion extends Question {
    /**
     * The answer to the question
     */
    private ArrayList<String> answers;

    /**
     * Constructor of the OpenQuestion class
     * @param name
     * @param description
     * @param time
     * @param timeOn
     * @param answer
     */
    public OpenQuestion(String name, String description, int time, boolean timeOn, String answer) {
        super(name, description, time, timeOn);
        this.answers = new ArrayList<String>();
    }

    /**
     * Adds an answer to the allowed answers.
     * @param answer
     */
    public void addAnswer(String answer) {
        answers.add(answer);
    }

    /**
     * Checks whether the answer is correct.
     * @todo implement a better way to do this.
     * @param answer
     * @return vailidity
     */
    public boolean solve(String answer) {
        if(answers.contains(answer))
            return true;

        return false;
    }
}
