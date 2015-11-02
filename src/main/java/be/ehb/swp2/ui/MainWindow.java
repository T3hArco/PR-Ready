package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.BadLoginException;
import be.ehb.swp2.exception.DuplicateUserException;
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
    private Configurator configurator;

    public MainWindow(SessionFactory factory) {
        this.factory = factory;
        this.configurator = new Configurator();

        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel topPane = new JPanel();
        final JTextField session = new JTextField(15);
        session.setEditable(false);
        topPane.add(session);

        JPanel bottomPane = new JPanel();
        JButton submit = new JButton("Get Session Info");
        bottomPane.add(submit);

        JPanel parent = new JPanel(new GridLayout(2, 1));
        this.add(parent);
        parent.add(topPane);
        parent.add(bottomPane);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserManager um = new UserManager(factory);
                User user = um.getUserByToken(configurator.getSetting("user", "token"));

                session.setText("Username: " + user.getUsername() + ", Token:" + user.getToken());
            }
        });

        this.pack();
        this.setVisible(true);
    }
}
