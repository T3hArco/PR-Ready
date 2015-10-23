package be.ehb.swp2.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arnaudcoel on 23/10/15.
 */

/**
 * The main login window for PR-Ready
 */
public class LoginWindow {
    private JFrame frame;

    /**
     * Default constructor for login
     */
    public LoginWindow() {
        frame = new JFrame("LoginWindow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("Bucht!");
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}
