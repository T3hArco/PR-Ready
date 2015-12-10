package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class Audio {
    private int id;
    private String link;

    public Audio(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
