package be.ehb.swp2.ui;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by domienhennion on 9/11/15.
 */
public class Login2 {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("HTMLUISample");
        final JButton newAccountButton = new JButton("Launch");
        newAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Data d = createAccount(frame);
                // Displays created account's details
                JOptionPane.showMessageDialog(frame, "Created Account: " + d);
            }
        });
        JPanel contentPane = new JPanel();
        contentPane.add(newAccountButton);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(contentPane, BorderLayout.CENTER);
        frame.setSize(200,60);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static Data createAccount(JFrame parent) {
        final AtomicReference<Data> result = new AtomicReference<Data>();
        final JDialog dialog = new JDialog(parent, "Login", true);
        // Create Browser instance.
        final Browser browser = new Browser();
        //browser.bro
        // Register Java callback function that will be invoked from JavaScript
        // when user clicks New Account button.

        browser.registerFunction("onLogin", new BrowserFunction() {
            @Override
            public JSValue invoke(JSValue... args) {
                // Read text field values received from JavaScript.
                String username = args[0].getString();
                String password = args[1].getString();
                // Create a new Account instance.
                result.set(new Data(username, password));
                // Close dialog.
                dialog.setVisible(false);
                // Return undefined (void) to JavaScript.
                return JSValue.createUndefined();
            }
        });
        // Load HTML with dialog's HTML+CSS+JavaScript UI.
        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/");
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Dispose Browser instance because we don't need it anymore.
                browser.dispose();
                // Close Login dialog.
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Embed Browser Swing component into the dialog.
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        return result.get();
    }
    public static class Data {
        private String username;
        private String password;
        public Data(String username, String password) {
            this.username = username;
            this.password = password;
        }
        public String toString(){
            return "username: " + this.username + ", password: " + this.password;
        }
    }
}
