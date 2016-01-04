package be.ehb.swp2.entity.question;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class VideoQuestion {
    private int id, parentId;
    private String link;

    /**
     * Default constructor for video
     */
    public VideoQuestion() {
    }

    /**
     * The constructor for questions
     *
     * @param parentId integer
     * @param link     string
     */
    public VideoQuestion(int parentId, String link) {
        this.parentId = parentId;
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
    public int getParentId() {
        return parentId;
    }

    /**
     * Sets the parent question id
     *
     * @param parentId integer
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

}
