package be.ehb.swp2.entity;

import be.ehb.swp2.util.Encryptor;


/**
 * Created by arnaudcoel on 22/10/15.
 */

/**
 * This class contains all information about an User
 */
public class User {
    /**
     * This references to the id in the database
     */
    private int id;
    private String name, password;
    private UserRole userRole;

    /**
     * Default constructor voor User
     */
    public User() {};

    /**
     * Constructor voor User
     * @param name
     * @param password
     */
    public User(String name, String password) {
        this.name = name;
        this.setPassword(password);
        this.userRole = UserRole.USER;
    }

    /**
     * Constructor voor User met aanpassing van standaard UserRole
     * @param name
     * @param password
     * @param userRole
     */
    public User(String name, String password, UserRole userRole) {
        this.name = name;
        this.setPassword(password);
        this.userRole = userRole;
    }

    /**
     * Geeft de ID van de gebruiker weer
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Zet de ID van de gebruiker
     * @param id
     */
    private void setId(int id) {
        this.id = id;
    }

    /**
     * Geeft de naam van de gebruiker
     * @return naam van de gebruiker
     */
    public String getName() {
        return name;
    }

    /**
     * Zet de naam van de gebruiker
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Geeft het gehashte wachtwoord van de gebruiker weer
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Geeft de UserRole van de gebruiker weer
     * @return
     */
    public UserRole getUserRole() {
        return this.userRole;
    }

    /**
     * Zet de UserRole van de gebruiker
     * @param userRole
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Zet het wachtwoord van de gebruiker met een gehashed wachtwoord.
     * @param password
     */
    public void setPassword(String password) {
        this.password = Encryptor.hashPassword(password);
    }
}
