package be.ehb.swp2.entity;

import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.UserManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.*;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class UserTest {
    private SecureRandom random;
    private SessionFactory factory;
    private UserManager um;
    private String username, password;

    public UserTest() {
        this.random = new SecureRandom();
        this.factory = new Configuration().configure().buildSessionFactory();
        this.um = new UserManager(factory);

        this.username = new BigInteger(130, random).toString(32);
        this.password = new BigInteger(130, random).toString(32);
    }

    @Test
    /**
     * Deze test gaat controleren of het wachtwoord wel degelijk goed hashed wordt.
     */
    public void createUser() {
        try {
            um.addUser(username, password);
        } catch (DuplicateUserException e) {
            fail("Duplicate user");
        }
    }

    @Test
    public void testGetId() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {

    }

    @Test
    public void testSetName() throws Exception {

    }

    @Test
    public void testGetPassword() throws Exception {

    }

    @Test
    public void testGetUserRole() throws Exception {

    }

    @Test
    public void testSetUserRole() throws Exception {
        User user = null;
    }

    @Test
    public void testSetPassword() throws Exception {

    }
}