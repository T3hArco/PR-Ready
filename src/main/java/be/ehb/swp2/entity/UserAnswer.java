package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 26/11/15.
 */

import org.hibernate.annotations.Entity;

import java.io.Serializable;

/**
 * This class establishes a userAnswer
 */

public class UserAnswer implements Serializable {
    private Integer userId;
    private Integer questionId;
    private String answer;

    /**
     * Default constructor for UserAnswer
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
    public UserAnswer() { }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAnswer that = (UserAnswer) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null) return false;
        return !(answer != null ? !answer.equals(that.answer) : that.answer != null);

    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
