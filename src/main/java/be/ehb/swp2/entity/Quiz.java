package be.ehb.swp2.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arnaudcoel on 26/10/15.
 */

/**
 * The class that contains the data of Quizzes
 */
public class Quiz implements Comparable<Quiz> {
    /**
     * Het unieke nummer van een Quiz
     */
    private int id;

    /**
     * De naam van een Quiz
     */
    private String title;

    /**
     * Het logo van een Quiz
     */
    private String logo;

    /**
     * De beschrijving van een Quiz
     */
    private String description;

    /**
     * De vragen die in de quiz zitten. Worden geinterpreteerd als een questionCollection
     */
    private List<Question> questions;

    /**
     * Default constructor voor Quiz
     */
    public Quiz() {
    }

    /**
     * Constructor voor Quiz
     *
     * @param title
     * @param description
     */
    public Quiz(String title, String description) {
        this.title = title;
        this.description = description;
        this.questions = new ArrayList<Question>();
    }

    /**
     * Getter voor id
     *
     * @return Id van de Quiz
     */
    public int getId() {
        return id;
    }

    /**
     * Setter voor id
     *
     * @param id de nieuwe id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Geeft een byte array terug van het object
     * Niet gebruiken zonder manager!
     *
     * @return logo URI
     */
    public String getLogo() {
        return logo;
    }

    /**
     * setter voor logo URI
     *
     * @param logo logo URI
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * getter voor name
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter voor name
     *
     * @param name
     */
    public void setTitle(String name) {
        this.title = title;
    }

    /**
     * getter voor description
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter voor description
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * compareTo methode voor klasse Quiz
     */
    public int compareTo(Quiz q) {
        return this.description.compareTo(q.getDescription());
    }

    /**
     * Gets the questions in a certain quiz
     *
     * @return questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Sets the list of questions
     *
     * @param questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Adds a question to the question list. This should not be called on the object self
     *
     * @param question Question
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    /**
     * Gets a certain question in the DB
     *
     * @param id identifier
     * @return question
     */
    public Question getQuestion(int id) {
        return this.questions.get(id);
    }

    public void addAllQuestions(ArrayList<Question> questions) {
        this.questions.addAll(questions);
    }
}