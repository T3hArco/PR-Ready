package be.ehb.swp2.entity;

import java.io.Serializable;

/**
 * Created by arnaudcoel on 26/11/15.
 */

/**
 * Class defines a subscription to a quiz
 */
public class UserSubscription implements Serializable {
    /**
     * The identifier in the database
     */
    private int subscriptionId;

    /**
     * The identifier of the corresponding quiz
     */
    private int quizId;

    /**
     * The identifier of the corresponding user
     */
    private int userId;

    /**
     * Default constructor for usersubscription
     */
    public UserSubscription() {
    }

    /**
     * The constructor of the UserSubscription
     *
     * @param quizId identifier of corresponsing quiz
     * @param userId identifier of the corresponding user
     */
    public UserSubscription(int quizId, int userId) {
        this.quizId = quizId;
        this.userId = userId;
    }

    /**
     * Gets unique identifier
     *
     * @return id unique identifier
     */
    public int getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Sets the subscription ID
     *
     * @param subscriptionId id
     */
    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    /**
     * Gets the unique identifier for a quiz
     *
     * @return unique identifier
     */
    public int getQuizId() {
        return quizId;
    }

    /**
     * Sets the unique identifier for a quiz
     *
     * @param quizId
     */
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    /**
     * Gets the unique identifier for the user
     *
     * @return unique user identifier
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for a user
     *
     * @param userId user identifier
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
