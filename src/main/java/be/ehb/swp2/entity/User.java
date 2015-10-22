package be.ehb.swp2.entity;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class User {
    private int id;
    private String name, password;

    public User() {}; // default constructor
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // TODO implement encryption
        this.password = password;
    }
}
