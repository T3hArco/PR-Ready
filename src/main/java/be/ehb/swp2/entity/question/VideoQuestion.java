package be.ehb.swp2.entity.question;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class VideoQuestion {
    private int id, parentQuestion;
    private String link;

    /**
     * Default constructor for video
     */
    public VideoQuestion() {
    }

    /**
     * The constructor for questions
     *
     * @param parentQuestion integer
     * @param link           string
     */
    public VideoQuestion(int parentQuestion, String link) {
        this.parentQuestion = parentQuestion;
        this.link = link;
    }

    /**
     * Gets the id for the video
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the id for this
     *
     * @param id set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the link for youtube
     *
     * @return String
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link!
     *
     * @param link string
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets the parent question ID
     *
     * @return integer
     */
    public int getParentQuestion() {
        return parentQuestion;
    }

    /**
     * Sets the parent question id
     *
     * @param parentQuestion integer
     */
    public void setParentQuestion(int parentQuestion) {
        this.parentQuestion = parentQuestion;
    }
}
