package be.ehb.swp2.entity;

import org.hibernate.type.EnumType;

import javax.persistence.Enumerated;


/**
 * Created by arnaudcoel on 22/10/15.
 */
public class User {
    private int id;
    private String name, password;
    private UserRole userRole;

    public User() {}; // default constructor
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.userRole = UserRole.USER;
    }

    public User(String name, String password, UserRole userRole) {
        this.name = name;
        this.password = password;
        this.userRole = userRole;
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

    public UserRole getUserRole() {
        return this.userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setPassword(String password) {
        // TODO implement encryption
        this.password = password;
    }
}
