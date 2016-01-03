package be.ehb.swp2.entity.question;

import java.util.List;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class AudioQuestion {
    private int id, parentId;
    private String link;

    /**
     * The default constructor for audio
     */
    public AudioQuestion() {
    }

    private List<AudioQuestion> audioQuestions;

    /**
     * Constructor foor dingenejsdngkjzqdjk:gjeklzghkljsdhcklghsdjlfghiwdkluhfgj;:hwdfh,vflnxd;vbgj,ds
     *
     * @param parentId fuck mijn leven
     * @param link           help mij
     */
    public AudioQuestion(String link, int parentId) {
        this.parentId = parentId;
        this.link = link;
    }

    /**
     * Gets the id
     *
     * @return the id for audio
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID for audio
     *
     * @param id id of audio
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets the link
     *
     * @return blah
     */
    public String getLink() {
        return link;
    }

    /**
     * sets the link
     *
     * @param link blah
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * gets the id of the parent question
     *
     * @return integer
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * sets the integer for the parent question
     *
     * @param parentId
     */
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


    public void addAudioQuestion(AudioQuestion audioQuestion) {
        this.audioQuestions.add(audioQuestion);
    }

}