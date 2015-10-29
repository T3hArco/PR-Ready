package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 29/10/15.
 */

import java.sql.Timestamp;

/**
 * Class to create a session for a user
 */
public class Session {
    private int id;
    private User user;
    private String token;
    private Timestamp expires;

    /**
     * Default constructor for Session
     */
    public Session() {}

    /**
     * Constructor for Session
     * @param user
     * @param token
     * @param expires
     */
    public Session(User user, String token, Timestamp expires) {
        this.user = user;
        this.token = token;
        this.expires = expires;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }
}
