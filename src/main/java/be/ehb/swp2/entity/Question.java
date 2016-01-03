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

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    /**
     * This is the body of the question
     */
    private String text;

    /**
     * This is the type of the question. This identifier is only used in the database and is used to point to the correct
     * sub-table
     */
    private AnswerType answerType;

    /**
     * The media type for the answer
     */
    private AnswerMediaType answerMediaType;

    private int parentId;
    private int quizId;

    /**
     * The default constructor for Question
     */
    public Question() {
    }

    /**
     * The constructor for question
     *
     * @param title           The title of the question
     * @param text            The body of the question
     * @param answerType    the type of the question.
     * @param answerMediaType the type of the media
     * @param parentId ??? TODO
     */
    public Question(String title, String text, AnswerType answerType, AnswerMediaType answerMediaType, int parentId, int quizId) {
        this.title = title;
        this.text = text;
        this.answerType = answerType;
        this.answerMediaType = answerMediaType;
        this.parentId = parentId;
        this.quizId = quizId;
    }

    public Question(int id, String title, String text, AnswerType answerType, AnswerMediaType answerMediaType, int parentId, int quizId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.answerType = answerType;
        this.answerMediaType = answerMediaType;
        this.parentId = parentId;
        this.quizId = quizId;
    }

    /**
     * Returns the unique identifier of the question
     *
     * @return integer
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the question
     *
     * @param id identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the title of the question
     *
     * @return String
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the question
     *
     * @param title title of the question
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the body of the question
     *
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the body of the question
     *
     * @param text body
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the type of the question
     *
     * @return type
     */
    public AnswerType getAnswerType() {
        return answerType;
    }

    /**
     * Sets the body of the question
     *
     * @param answerType type
     */
    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public AnswerMediaType getAnswerMediaType() {
        return answerMediaType;
    }

    public void setAnswerMediaType(AnswerMediaType answerMediaType) {
        this.answerMediaType = answerMediaType;
    }
}
