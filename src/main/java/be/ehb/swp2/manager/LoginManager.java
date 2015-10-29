package be.ehb.swp2.manager;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.util.Encryptor;
import org.hibernate.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by arnaudcoel on 29/10/15.
 */
public class LoginManager {
    private SessionFactory factory;

    /**
     * Constructor for the LoginManager
     * @param factory gives the Hibernate factory
     */
    public LoginManager(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Checks for a valid login
     * @param username the username
     * @param password the unhashed password of the user which will be hashed inside the method
     * @return
     */
    @Deprecated
    public boolean isValidLogin(String username, String password) {
        Session session = factory.openSession();
        Transaction transaction = null;

        password = Encryptor.hashPassword(password); // hash the password

        List<Object[]> userList = session.createQuery("SELECT id, username, password FROM User WHERE (username = :username AND password = :password)")
                .setMaxResults(1)
                .setParameter("username", username)
                .setParameter("password", password)
                .list();

        // Check whether the list is empty, if so, no users are matched, thus return false
        if(userList.size() == 0)
            return false;


        int userId = Integer.parseInt(userList.get(0)[0].toString());

        return true;
    }

    /**
     * Authenticates the user, returns null if authentication failed.
     * @param username the username
     * @param password the unhashed password of the user which will be hashed inside the method
     * @return session token
     */
    public String authenticate(String username, String password) {
        Session session = factory.openSession();
        Transaction transaction = null;

        password = Encryptor.hashPassword(password); // hash the password

        List<Object[]> userList = session.createQuery("SELECT id, username, password FROM User WHERE (username = :username AND password = :password)")
                .setMaxResults(1)
                .setParameter("username", username)
                .setParameter("password", password)
                .list();

        // Check whether the list is empty, if so, no users are matched, thus return false
        if(userList.size() == 0)
            return null;

        int userId = Integer.parseInt(userList.get(0)[0].toString());

        // generate the user token here
        SecureRandom random = new SecureRandom();
        String token = Encryptor.hashPassword(userId + new BigInteger(130, random).toString(32));

        return token;
    }
}
