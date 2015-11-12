package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;

/**
 * Created by Thomas on 29/10/2015.
 */

/**
 * MathQuestion, extends abstract class Question
 */
public class MathQuestion extends Question {
    /**
     * Contains the answer in the form of a double
     */
    private double answer;

    /**
     * Constructor of MathQuestion
     * @param answer the answer of the question
     * @param name name of the question, inherited from Question
     * @param description description of the question, inherited from Question
     * @param time time of the question, inherited from Question
     * @param timeOn time on a question, inherited from Question
     */
    public MathQuestion(double answer, String name, String description, int time, boolean timeOn) {
        super(name, description, time, timeOn);
        this.answer = answer;
    }

    /**
     * sets the answer of a question
     * @param answer
     */
    private void setAnswer(double answer) {
        this.answer = answer;
    }

    /**
     * Gets the answer of this question
     * @return
     */
    public double getAnswer() {
        return this.answer;
    }

    /**
     * Solves the question and then returns its vailidity.
     * @param userAnswer
     * @return whether the question was correct.
     */
    public boolean solve(int userAnswer) {
        if(userAnswer == this.answer)
            return true;

        return false;
    }
}
