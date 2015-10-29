package be.ehb.swp2.ui;

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

    public LoginWindow() {
        this.initComponents();
    }

    @Override
    public void initComponents() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTextField username = new JTextField(10);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setLabelFor(username);

        JPasswordField password = new JPasswordField(10);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setLabelFor(password);

        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");

        JPanel parent = new JPanel(new GridLayout(3, 2));
        this.add(parent);

        parent.add(usernameLabel);
        parent.add(username);
        parent.add(passwordLabel);
        parent.add(password);
        parent.add(ok);
        parent.add(cancel);

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

            }

        });


        cancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(-1);
            }

        });

        this.pack();
        this.setVisible(true);
    }
}
