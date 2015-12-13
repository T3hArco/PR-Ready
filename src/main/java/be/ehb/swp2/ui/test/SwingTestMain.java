package be.ehb.swp2.ui.test;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.TokenNotFoundException;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.ui.LoginWindow;
import be.ehb.swp2.util.ConfigurationHandler;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by arnaudcoel on 23/10/15.
 */

/**
 * The main login window for PR-Ready
 */
public class SwingTestMain extends JFrame implements be.ehb.swp2.ui.Window {
    private SessionFactory factory;
    private ConfigurationHandler configurationHandler;

    public SwingTestMain(SessionFactory factory) {
        this.factory = factory;
        this.configurationHandler = new ConfigurationHandler();

        this.initComponents();
    }

    public void initComponents() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel topPane = new JPanel();
        final JTextField session = new JTextField(35);
        session.setEditable(false);
        topPane.add(session);

        JPanel centerPane = new JPanel();
        final JLabel status = new JLabel("Status unknown");
        centerPane.add(status);

        JPanel bottomPane = new JPanel();
        JButton submit = new JButton("Get Session Info");
        JButton isUser = new JButton("Check for user rights!");
        JButton isAdmin = new JButton("Check for admin rights!");
        JButton launchAdminInterface = new JButton("Launch admin interface");
        JButton launchLoginInterface = new JButton("Relogin");
        bottomPane.add(submit);
        bottomPane.add(isUser);
        bottomPane.add(isAdmin);
        bottomPane.add(launchAdminInterface);
        bottomPane.add(launchLoginInterface);

        JPanel parent = new JPanel(new GridLayout(3, 1));
        this.add(parent);

        // get rekt, logic
        parent.add(centerPane);
        parent.add(topPane);
        parent.add(bottomPane);

        launchLoginInterface.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginWindow(factory);
                configurationHandler.setSetting("user", "token", "loggedout");
            }
        });

        launchAdminInterface.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new SwingAdminUI(factory);
                } catch (UserNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UserNoPermissionException e1) {
                    e1.printStackTrace();
                } catch (TokenNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                status.setText("Checking..");

                UserManager um = new UserManager(factory);
                User user = null;
                ConfigurationHandler configurationHandler = new ConfigurationHandler(); // moved configurationHandler due to reloading problems
                String token = configurationHandler.getSetting("user", "token");

                try {
                    user = um.getUserByToken(token);
                } catch (TokenNotFoundException e1) {
                    e1.printStackTrace();
                    status.setText("Token does not exist");
                    status.setForeground(Color.RED);
                } catch (UserNotFoundException e1) {
                    e1.printStackTrace();
                    status.setText("User does not exist");
                    status.setForeground(Color.RED);
                }

                if (user != null && token != null) {
                    status.setText("Authenticated");
                    status.setForeground(new Color(28, 184, 65));
                }

                session.setText("Username: " + user.getUsername() + ", Token:" + token + ", Role: " + user.getUserRole());
            }
        });

        isAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserManager um = new UserManager(factory);
                User user = null;
                ConfigurationHandler configurationHandler = new ConfigurationHandler(); // moved configurationHandler due to reloading problems
                String token = configurationHandler.getSetting("user", "token");

                try {
                    user = um.getUserByToken(token);
                } catch (TokenNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "Token not found", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (UserNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "User not found", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }

                if (user.hasRole(UserRole.ADMINISTRATOR))
                    JOptionPane.showMessageDialog(null, "User is an administrator", "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "User is not an administrator", "PR-Ready", JOptionPane.ERROR_MESSAGE);
            }
        });

        isUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserManager um = new UserManager(factory);
                User user = null;
                ConfigurationHandler configurationHandler = new ConfigurationHandler(); // moved configurationHandler due to reloading problems
                String token = configurationHandler.getSetting("user", "token");

                try {
                    user = um.getUserByToken(token);
                } catch (TokenNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "Token not found", "PR-Ready", JOptionPane.ERROR);
                    e1.printStackTrace();
                } catch (UserNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "User not found", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }

                if (user.hasRole(UserRole.USER))
                    JOptionPane.showMessageDialog(null, "User is a user", "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "User is a user", "PR-Ready", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.pack();
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }
}
