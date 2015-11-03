package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.BadLoginException;
import be.ehb.swp2.exception.DuplicateUserException;
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
                UserManager um = new UserManager(factory);

                /*User user = lm.authenticate(username.getText(), password.getText());
                String token = um.setToken(user.getId());

                if(token != null && user != null)
                    JOptionPane.showMessageDialog(null, "Inloggen is gelukt: " + username.getText() + ", Token: " + token);
                else
                    JOptionPane.showMessageDialog(null, "Inloggen mislukt!");*/

                try {
                    User user = lm.authenticate(username.getText(), password.getText());

                    String token = um.setToken(user.getId());

                    JOptionPane.showMessageDialog(null, "Inloggen is gelukt: " + username.getText() + ", Token: " + token, "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                } catch (BadLoginException e) {
                    JOptionPane.showMessageDialog(null, "Gebruikersnaam of wachtwoord incorrect.", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

        });


        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    UserManager um = new UserManager(factory);
                    um.addUser(username.getText(), password.getText());
                    JOptionPane.showMessageDialog(null, "Gebruiker is aangemaakt", "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                } catch (DuplicateUserException e1) {
                    JOptionPane.showMessageDialog(null, "De gebruiker die u probeert aan te maken bestaat al!", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }

        });

        this.pack();
        this.setVisible(true);
    }
}
