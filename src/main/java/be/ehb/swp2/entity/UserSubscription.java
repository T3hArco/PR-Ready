package be.ehb.swp2.entity;

import java.io.Serializable;

/**
 * Created by arnaudcoel on 26/11/15.
 */
public class UserSubscription implements Serializable {
    private int subscriptionId;
    private int quizId;
    private int userId;

    public UserSubscription(int quizId, int userId) {
        this.quizId = quizId;
        this.userId = userId;
    }

    public UserSubscription() { }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
