package be.ehb.swp2.ui;

import be.ehb.swp2.manager.LoginManager;
import be.ehb.swp2.manager.UserManager;
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
public class LoginWindow extends JFrame implements Window {
    private SessionFactory factory;

    public LoginWindow(SessionFactory factory) {
        this.factory = factory;

        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        final JTextField username = new JTextField(10);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setLabelFor(username);

        final JPasswordField password = new JPasswordField(10);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setLabelFor(password);

        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel aka create");

        JPanel parent = new JPanel(new GridLayout(3, 2));
        this.add(parent);

        parent.add(usernameLabel);
        parent.add(username);
        parent.add(passwordLabel);
        parent.add(password);
        parent.add(ok);
        parent.add(cancel);

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                LoginManager lm = new LoginManager(factory);

                String token = lm.authenticate(username.getText(), password.getText());
                if(token != null)
                    JOptionPane.showMessageDialog(null, "Inloggen is gelukt: " + username.getText() + ", Token: " + token);
                else
                    JOptionPane.showMessageDialog(null, "Inloggen mislukt!");
            }

        });


        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserManager um = new UserManager(factory);
                um.addUser(username.getText(), password.getText());
                //System.exit(-1);
            }

        });

        this.pack();
        this.setVisible(true);
    }
}
