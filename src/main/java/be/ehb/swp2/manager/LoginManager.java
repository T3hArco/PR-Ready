package be.ehb.swp2.manager;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.BadLoginException;
import be.ehb.swp2.exception.UserNotFoundException;
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
     * Authenticates the user, returns null if authentication failed.
     * @param username the username
     * @param password the unhashed password of the user which will be hashed inside the method
     * @return User object
     */
    public User authenticate(String username, String password) throws BadLoginException {
        Session session = factory.openSession();

        password = Encryptor.hashPassword(password); // hash the password

        List<Object[]> userList = session.createQuery("SELECT id, username, password FROM User WHERE (username = :username AND password = :password AND deleted = false)")
                .setMaxResults(1)
                .setParameter("username", username)
                .setParameter("password", password)
                .list();

        // Check whether the list is empty, if so, no users are matched, thus return false
        if(userList.size() == 0)
            throw new BadLoginException();

        int userId = Integer.parseInt(userList.get(0)[0].toString());
        session.close();

        User user = null;
        try {
            user = new UserManager(factory).getUserById(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new BadLoginException();
        }

        return user;
    }

}
