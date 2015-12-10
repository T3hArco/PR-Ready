package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Answer;
import be.ehb.swp2.entity.AnswerType;
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
    private AnswerType answerType;

    public MathQuestion(String title, String text, AnswerType answerType, double answer) {
        super(title, text, answerType, 1);
        this.answer = answer;
        this.answerType = AnswerType.MATH;
    }

    public MathQuestion(double answer) {
    }

    /**
     * Gets the answer of this question
     *
     * @return
     */
    public double getAnswer() {
        return this.answer;
    }

    /**
     * sets the answer of a question
     *
     * @param answer
     */
    private void setAnswer(double answer) {
        this.answer = answer;
    }

    /**
     * Solves the question and then returns its vailidity.
     *
     * @param userAnswer
     * @return whether the question was correct.
     */
    public boolean solve(int userAnswer) {
        return userAnswer == this.answer;

    }
}
