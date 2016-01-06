package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class Answer {
    private int answerId;
    private String text;


    public Answer() {
    }

    public Answer(String text) {
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

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", text='" + text + '\'' +
                '}';
    }
}
