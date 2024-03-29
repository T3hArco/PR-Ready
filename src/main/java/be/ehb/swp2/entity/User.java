package be.ehb.swp2.entity;

import be.ehb.swp2.util.EncryptionHandler;


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
    private String username;
    private String password;
    private UserRole userRole;
    private boolean deleted;

    /**
     * The authentication token of the user
     */
    private String token;

    /**
     * Default constructor voor User
     */
    public User() {
    }

    /**
     * Constructor voor User
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.setPassword(password);
        this.userRole = UserRole.USER;
        this.deleted = false;
    }

    /**
     * Constructor voor User met aanpassing van standaard UserRole
     *
     * @param username
     * @param password
     * @param userRole
     */
    public User(String username, String password, UserRole userRole) {
        this.username = username;
        this.setPassword(password);
        this.userRole = userRole;
    }

    /**
     * Geeft de ID van de gebruiker weer
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Zet de ID van de gebruiker
     *
     * @param id
     */
    private void setId(int id) {
        this.id = id;
    }

    /**
     * Geeft de username van de gebruiker
     *
     * @return username van de gebruiker
     */
    public String getUsername() {
        return username;
    }

    /**
     * Zet de naam van de gebruiker
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Geeft het gehashte wachtwoord van de gebruiker weer
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Zet het wachtwoord van de gebruiker met een gehashed wachtwoord.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = EncryptionHandler.hashPassword(password);
    }

    /**
     * Geeft de UserRole van de gebruiker weer
     *
     * @return
     */
    public UserRole getUserRole() {
        return this.userRole;
    }

    /**
     * Zet de UserRole van de gebruiker
     *
     * @param userRole
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Haalt de token van de gebruiker op
     *
     * @return unique token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Methode vereist door Java conventie, private en leeg.
     *
     * @param token
     * @deprecated gebruik setToken() -> automatisch gegenereerd en secure
     */
    @Deprecated
    private void setToken(String token) {
    }

    /**
     * Zet de token van de gebruiker, dit roept generateToken van de EncryptionHandler class op.
     */
    public String setToken() {
        this.token = EncryptionHandler.generateToken(this);

        return this.token;
    }

    /**
     * Checks for the correct rights of the user
     *
     * @return boolean
     * @deprecated
     */
    public boolean isAdmin() {
        return this.userRole == UserRole.ADMINISTRATOR;

    }

    /**
     * Checks for the correct rights of the user
     *
     * @return boolean
     * @deprecated
     */
    public boolean isUser() {
        return this.userRole == UserRole.USER;

    }

    /**
     * Dynamically checks if user has required role.
     *
     * @param userRole
     * @return boolean
     */
    public boolean hasRole(UserRole userRole) {
        return this.userRole == userRole;

    }

    /**
     * Returns whether the user has been deleted
     *
     * @return boolean
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deletion of the user
     *
     * @param deleted boolean
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userRole=" + userRole +
                ", deleted=" + deleted +
                '}';
    }
}