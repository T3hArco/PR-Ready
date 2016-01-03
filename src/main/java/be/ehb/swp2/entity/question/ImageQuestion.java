package be.ehb.swp2.entity.question;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class ImageQuestion {
    private int id, parentId;
    private String link;

    public ImageQuestion() {
    }

    public ImageQuestion(int parentId, String link) {
        this.parentId = parentId;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
