package be.ehb.swp2.ui.test;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.TokenNotFoundException;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.ui.PermissionWindow;
import be.ehb.swp2.util.Configurator;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by arnaudcoel on 22/11/15.
 */

/**
 * The main login window for PR-Ready
 */
public class SwingAdminUI extends JFrame implements be.ehb.swp2.ui.Window, PermissionWindow {
    private SessionFactory factory;
    private UserRole requiredRole = ADMINISTRATOR;

    public SwingAdminUI(SessionFactory factory) throws UserNotFoundException, UserNoPermissionException, TokenNotFoundException {
        this.factory = factory;

        this.hasPermission();
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
        bottomPane.add(submit);
        bottomPane.add(isUser);
        bottomPane.add(isAdmin);

        JPanel parent = new JPanel(new GridLayout(3, 1));
        this.add(parent);

        // get rekt, logic
        parent.add(centerPane);
        parent.add(topPane);
        parent.add(bottomPane);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                status.setText("Checking..");

                UserManager um = new UserManager(factory);
                User user = null;
                Configurator configurator = new Configurator(); // moved configurator due to reloading problems
                String token = configurator.getSetting("user", "token");

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
                Configurator configurator = new Configurator(); // moved configurator due to reloading problems
                String token = configurator.getSetting("user", "token");

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
                Configurator configurator = new Configurator(); // moved configurator due to reloading problems
                String token = configurator.getSetting("user", "token");

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

    public boolean hasPermission() throws UserNoPermissionException, UserNotFoundException, TokenNotFoundException {
        UserManager um = new UserManager(factory);
        User user = null;
        Configurator configurator = new Configurator(); // moved configurator due to reloading problems
        String token = configurator.getSetting("user", "token");

        user = um.getUserByToken(token);

        if (!user.hasRole(this.requiredRole))
            throw new UserNoPermissionException();

        return true;
    }
}
