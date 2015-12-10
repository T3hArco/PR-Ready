package be.ehb.swp2.entity.question;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class ImageQuestion {
    private int id, parentQuestion;
    private String link;

    public ImageQuestion() {
    }

    public ImageQuestion(int parentQuestion, String link) {
        this.parentQuestion = parentQuestion;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentQuestion() {
        return parentQuestion;
    }

    public void setParentQuestion(int parentQuestion) {
        this.parentQuestion = parentQuestion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
