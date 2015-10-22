package be.ehb.swp2.entity;

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
        Integer u1 = um.addUser("dummyuser", "roflcopters");
        assertEquals("Password must be hashed to 932ef14a9b01090c1f78dcaf0c0e5781", "932ef14a9b01090c1f78dcaf0c0e5781", um.getUserById(u1).getPassword());

    }
}