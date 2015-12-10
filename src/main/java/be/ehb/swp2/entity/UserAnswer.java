package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 26/11/15.
 */

import java.io.Serializable;

/**
 * This class establishes a userAnswer
 */

/**
 * This class defines an answer by a user.
 * @deprecated  ?
 */
public class UserAnswer implements Serializable {
    /**
     * The id of the corresponding user
     */
    private Integer userId;

    /**
     * The id of the corresponding quiz
     */
    private Integer questionId;

    /**
     * The given answer
     */
    private String answer;

    /**
     * Default constructor for UserAnswer
     *
     * @param userId
     * @param questionId
     * @param answer
     */
    public UserAnswer(Integer userId, Integer questionId, String answer) {
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
    }

    /**
     * Default constructor
     */
    public UserAnswer() {
    }

    /**
     * Gets the id of the user
     *
     * @return user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the id of the user
     *
     * @param userId userid
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the question identifier
     *
     * @return question id
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * Sets the question id
     *
     * @param questionId q
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * Gets the given answer to the question
     *
     * @return answser answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the answer given to the question
     *
     * @param answer answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
