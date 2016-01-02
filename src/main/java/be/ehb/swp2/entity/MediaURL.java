package be.ehb.swp2.entity;

/**
 * Created by domienhennion on 2/01/16.
 */
public class MediaURL {
    private int id;
    private String url;



    public MediaURL(int id, String url) {
        super();
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
