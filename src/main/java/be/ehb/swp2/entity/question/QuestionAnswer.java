package be.ehb.swp2.entity.question;

import java.io.Serializable;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class QuestionAnswer implements Serializable {
    private int questionId, answerId;
    private boolean correct;

    /**
     * The default constructor for questionAnswer
     */
    public QuestionAnswer(int questionId, int answerId) {

        this.questionId = questionId;
        this.answerId = answerId;
    }

    /**
     * The constructor that also specifies whether the question is correct
     *
     * @param correct boolean
     */
    public QuestionAnswer(int questionId, int answerId, boolean correct) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.correct = correct;
    }

    /**
     * Fetches the id for the question
     *
     * @return integer
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * Sets the id for the question
     *
     * @param questionId integer
     */
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    /**
     * gets the answer id
     *
     * @return integer
     */
    public int getAnswerId() {
        return answerId;
    }

    /**
     * sets the answer id
     *
     * @param answerId integer
     */
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    /**
     * Checks whether the question is correct
     *
     * @return correctness
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Sets whether the question was answered correctly
     *
     * @param correct boolean
     */
    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
