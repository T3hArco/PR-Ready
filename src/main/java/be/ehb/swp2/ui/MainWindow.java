package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.BadLoginException;
import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.TokenNotFoundException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.LoginManager;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.util.Configurator;
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
public class MainWindow extends JFrame implements Window {
    private SessionFactory factory;

    public MainWindow(SessionFactory factory) {
        this.factory = factory;

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
        bottomPane.add(submit);

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

                if(user != null && token != null) {
                    status.setText("Authenticated");
                    status.setForeground(new Color(28, 184, 65));
                }

                session.setText("Username: " + user.getUsername() + ", Token:" + token);
            }
        });

        this.pack();
        this.setVisible(true);
    }
}
