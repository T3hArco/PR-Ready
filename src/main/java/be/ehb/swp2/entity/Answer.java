package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class Answer {
    private int id, answerId;

    /**
     * Default constructor for answer
     */
    public Answer() {
    }

    /**
     * Constructor for Answer with an aswerId
     *
     * @param answerId
     */
    public Answer(int answerId) {
        this.answerId = answerId;
    }

    /**
     * Gets the id for the answer
     *
     * @return integer
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id for the answer
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the id for the answer
     *
     * @return integer
     */
    public int getAnswerId() {
        return answerId;
    }

    /**
     * Sets the id for the answer
     *
     * @param answerId id
     */
    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
