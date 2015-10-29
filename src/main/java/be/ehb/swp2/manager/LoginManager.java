package be.ehb.swp2.manager;

import be.ehb.swp2.util.Encryptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
    public boolean isValidLogin(String username, String password) {
        Session session = factory.openSession();
        Transaction transaction = null;

        password = Encryptor.hashPassword(password); // hash the password

        // make a parametered list for the login query
        Query fetchUsers = session.createQuery("SELECT username, password FROM User WHERE username = :username AND password = :password");
        fetchUsers.setParameter("username", username);
        fetchUsers.setParameter("password", password);
        List list = fetchUsers.list();

        System.err.println(list.size());

        // Check whether the list is empty, if so, no users are matched, thus return false
        if(list.size() == 0)
            return false;

        System.out.println(list.get(0).getClass());

        return false;
    }
}
