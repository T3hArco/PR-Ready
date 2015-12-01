package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.BadLoginException;
import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.LoginManager;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.util.Configurator;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by domienhennion on 9/11/15.
 * Modified by arnaudcoel 19/11/15 -> implemented window fully.
 */

/**
 * This class provides the implementation of the Login UI using JxBrowser
 * @implements Window
 * @extends JFrame
 */
public class LoginWindow extends JFrame implements Window {
    /**
     * Provides a connection to the database
     */
    private SessionFactory factory;

    /**
     * Provides a method for the session data of the user to be saved
     */
    private Configurator configurator;

    /**
     * Main constructor of the login window. Initializes the variables and then initializes the form
     * @param factory SQL session
     */
    public LoginWindow(SessionFactory factory) {
        this.factory = factory;
        this.configurator = new Configurator();

        this.initComponents();
    }

    /**
     * Initializes the components
     */
    public void initComponents() {
        User u = action(this);
    }

    /**
     * The action listener for this form. This will listen to login and registration requests.
     * @todo the error messages are currently shown with messagedialogs, please put this inside of the HTML
     * @param parent the parent frame in order to get the data required
     * @return User object that has been signed in or created.
     */
    private User action(JFrame parent) {
        /**
         * The result of the calls from JxBrowser
         */
        final AtomicReference<User> result = new AtomicReference<User>();

        /**
         * The main 'login' dialog
         */
        final JDialog dialog = new JDialog(parent, "Login", true);

        /**
         * The initialized browser using the JxBrowser library
         */
        final Browser browser = new Browser();

        /**
         * The listener for the register function
         */
        browser.registerFunction("onLogin", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                String username = args[0].getString();
                String password = args[1].getString();
                User user = null;

                LoginManager lm = new LoginManager(factory);
                UserManager um = new UserManager(factory);

                try {
                    result.set(user = lm.authenticate(username, password));
                    String token = um.setToken(user.getId());
                    configurator.setSetting("user", "token", token);

                    JOptionPane.showMessageDialog(null, "Inloggen is gelukt: " + username + ", Token: " + token, "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                } catch (BadLoginException e) {
                    JOptionPane.showMessageDialog(null, "Gebruikersnaam of wachtwoord incorrect.", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                if(user != null)
                    dialog.setVisible(false);

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onRegister", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                String username = args[0].getString();
                String password = args[1].getString();
                User user = null;

                UserManager um = new UserManager(factory);

                try {
                    Integer userId = um.addUser(username, password);
                    user = um.getUserById(userId);

                    result.set(user);
                } catch (DuplicateUserException e) {
                    JOptionPane.showMessageDialog(null, "De gebruikersnaam die u probeert te gebruiken is al in gebruik!", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } catch (UserNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "De gebruiker die aangemaakt werd in de databank kon niet teruggevonden worden!", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                return JSValue.createUndefined();
            }
        });

        /**
         * This performs randomization for the request URI. The library we're using has agressive caching, adding a secure random will defeat this problem.
         */
        SecureRandom random = new SecureRandom();

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/?" + new BigInteger(130, random));
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        return result.get();
    }

}
