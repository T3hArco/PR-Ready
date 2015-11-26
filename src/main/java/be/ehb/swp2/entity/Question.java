package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 29/10/2015.
 */

/**
 * This class contains all data for a question
 */
public class Question {
    /**
     * The identifier for this question in the database
     */
    private int id;

    /**
     * This is the title of the question
     */
    private String title;

    /**
     * This is the body of the question
     */
    private String text;

    /**
     * This is the type of the question. This identifier is only used in the database and is used to point to the correct
     * sub-table
     */
    private QuestionType questionType;

    /**
     * @todo define this
     */
    private int questionExtraId;

    /**
     * The default constructor for Question
     */
    public Question() { }

    /**
     * The constructor for question
     * @param title The title of the question
     * @param text The body of the question
     * @param questionType the type of the question
     * @param questionExtraId ??? TODO
     */
    public Question(String title, String text, QuestionType questionType, int questionExtraId) {
        this.title = title;
        this.text = text;
        this.questionType = questionType;
        this.questionExtraId = questionExtraId;
    }

    /**
     * Returns the unique identifier of the question
     * @return integer
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the question
     * @param id identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the title of the question
     * @return String
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the question
     * @param title title of the question
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the body of the question
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the body of the question
     * @param text body
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the type of the question
     * @return type
     */
    public QuestionType getQuestionType() {
        return questionType;
    }

    /**
     * Sets the body of the question
     * @param questionType type
     */
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    /**
     * ???
     * @return ???
     */
    public int getQuestionExtraId() {
        return questionExtraId;
    }

    /**
     * `??
     * @param questionExtraId ???
     */
    public void setQuestionExtraId(int questionExtraId) {
        this.questionExtraId = questionExtraId;
    }
}
