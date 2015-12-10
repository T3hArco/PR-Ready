package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 29/10/2015.
 */

/**
 * This class contains all data for a question
 * THIS CLASS MADE ME VERY SAD AND SUICIDAL. FUCKING HELL HOW CAN SOMEONE PRODUCE SUCH <B><FONT COLOR="RED">UTTER</FONT> BULLSHIT</B>
 */
public class Question {
    /**
     * The identifier for this question in the database
     */
    private int id;

    /**
     * The parent id of the quiz
     */
    private int parentId;

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
     * The type of the answer
     */
    private AnswerType answerType;

    /**
     * The type for the answer media
     */
    private AnswerMediaType answerMediaType;

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
     * @param questionType    the type of the question
     * @deprecated FUCK SAKE
     */
    public Question(String title, String text, QuestionType questionType) {
        this.title = title;
        this.text = text;
        this.questionType = questionType;
    }

    /**
     * Please use me
     *
     * @param parentId        the parent
     * @param title           the title
     * @param text            the text
     * @param questionType    the question type
     * @param answerType      the answer type
     * @param answerMediaType the media type
     */
    public Question(int parentId, String title, String text, QuestionType questionType, AnswerType answerType, AnswerMediaType answerMediaType) {
        this.parentId = parentId;
        this.title = title;
        this.text = text;
        this.questionType = questionType;
        this.answerType = answerType;
        this.answerMediaType = answerMediaType;
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
    public QuestionType getQuestionType() {
        return questionType;
    }

    /**
     * Sets the body of the question
     *
     * @param questionType type
     */
    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    /**
     * Gets the parent quiz id
     * @return integer
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * Sets the parent id
     * @param parentId integer
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets the answer type
     *
     * @return AnswerType
     */
    public AnswerType getAnswerType() {
        return answerType;
    }

    /**
     * Sets the type for the answer
     *
     * @param answerType type
     */
    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    /**
     * Gets the media answer type
     *
     * @return AnswerMediaType
     */
    public AnswerMediaType getAnswerMediaType() {
        return answerMediaType;
    }

    /**
     * Sets the answer media type
     *
     * @param answerMediaType type
     */
    public void setAnswerMediaType(AnswerMediaType answerMediaType) {
        this.answerMediaType = answerMediaType;
    }
}
