package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 29/10/2015.
 */
public class Question {
    private int id;
    private String title;
    private String text;
    private QuestionType questionType;
    private int questionExtraId;

    public Question() { }

    public Question(String title, String text, QuestionType questionType, int questionExtraId) {
        this.title = title;
        this.text = text;
        this.questionType = questionType;
        this.questionExtraId = questionExtraId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public int getQuestionExtraId() {
        return questionExtraId;
    }

    public void setQuestionExtraId(int questionExtraId) {
        this.questionExtraId = questionExtraId;
    }
}
