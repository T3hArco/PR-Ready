package be.ehb.swp.quiz;

import java.util.ArrayList;

/**
 * Created by domienhennion on 15/10/15.
 */
public class Question {
    private String answer;
    private String question;
    private int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
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

        Question q = (Question) o;

        if (id != q.id) return false;
        if (answer != null ? !answer.equals(q.answer) : q.answer != null) return false;
        return !(question != null ? !question.equals(q.question) : q.question != null);

    }

    @Override
    public int hashCode() {
        int result = answer != null ? answer.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
