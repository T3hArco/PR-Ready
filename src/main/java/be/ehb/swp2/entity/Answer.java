package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class Answer {
    private int answerId;
    private String text;

    public Answer() {
    }

    public Answer(int answerId, String text) {
        this.answerId = answerId;
        this.text = text;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
