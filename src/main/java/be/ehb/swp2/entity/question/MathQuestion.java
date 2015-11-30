package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuestionType;

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
    private QuestionType questionType;

    public MathQuestion(String title, String text, QuestionType questionType, Integer questionExtraId, double answer) {
        super(title, text, questionType, questionExtraId);
        this.answer = answer;
        this.questionType = QuestionType.MATH;
    }

    public MathQuestion(double answer) { }

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
