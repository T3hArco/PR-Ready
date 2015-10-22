package be.ehb.swp2;

import be.ehb.swp2.manager.UserManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class Runner {
    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch(Throwable e) {
            System.err.println("Failed to instantiate database: " + e);
            throw new ExceptionInInitializerError(e);
        }

        UserManager um = new UserManager(factory);
        Integer u1 = um.addUser("Arnaaud", "password");
        Integer u2 = um.addUser("Domien", "wachtwoord");

        um.listEmployeesToConsole();
    }
}
