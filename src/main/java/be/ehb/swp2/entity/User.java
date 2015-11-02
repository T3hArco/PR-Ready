package be.ehb.swp2.entity;

import be.ehb.swp2.util.Encryptor;

import java.math.BigInteger;
import java.security.SecureRandom;


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

    /**
     * The authentication token of the user
     */
    private String token;

    /**
     * Default constructor voor User
     */
    public User() {};

    /**
     * Constructor voor User
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.setPassword(password);
        this.userRole = UserRole.USER;
    }

    /**
     * Constructor voor User met aanpassing van standaard UserRole
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
     * Geeft de username van de gebruiker
     * @return username van de gebruiker
     */
    public String getUsername() {
        return username;
    }

    /**
     * Zet de naam van de gebruiker
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
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
        if(!Encryptor.isMD5(password))
            this.password = Encryptor.hashPassword(password);
        else
            this.password = password;
    }

    /**
     * Haalt de token van de gebruiker op
     * @return unique token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Methode vereist door Java conventie, private en leeg.
     * @deprecated gebruik setToken() -> automatisch gegenereerd en secure
     * @param token
     */
    @Deprecated
    private void setToken(String token) { }

    /**
     * Zet de token van de gebruiker, dit roept generateToken van de Encryptor class op.
     */
    public String setToken() {
        this.token = Encryptor.generateToken(this);
        return this.token;
    }
}
