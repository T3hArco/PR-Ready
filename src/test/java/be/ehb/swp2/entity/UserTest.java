package be.ehb.swp2.entity;

import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.manager.UserManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class UserTest {
    @Test
    /**
     * Deze test gaat controleren of het wachtwoord wel degelijk goed hashed wordt.
     */
    public void testPersistence() {
        SessionFactory factory;

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch(Throwable e) {
            fail("Database connection was NOT made");
            throw new ExceptionInInitializerError(e);
        }

        UserManager um = new UserManager(factory);
        Integer u1 = null;
        try {
            u1 = um.addUser("dummyuser", "roflcopters");
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }
        assertEquals("Password must be hashed to 932ef14a9b01090c1f78dcaf0c0e5781", "932ef14a9b01090c1f78dcaf0c0e5781", um.getUserById(u1).getPassword());

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

    }

    @Test
    public void testSetPassword() throws Exception {

    }
}