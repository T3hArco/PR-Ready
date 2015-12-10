package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.BadLoginException;
import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.LoginManager;
import be.ehb.swp2.manager.UserManager;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFileChooser;
import java.io.File;

/**
 * Created by domienhennion on 8/12/15.
 */
public class NewQuizWindow implements questionWindow{
    JFrame parent = new JFrame();
    public JFrame getParent(){
        return this.parent;
    }
    public NewQuizWindow() {
        final AtomicReference<User> result = new AtomicReference<User>();
        final JDialog dialog = new JDialog(parent, "newQuiz", true);
        final Browser browser = new Browser();

        browser.registerFunction("newFile", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result = fileChooser.showOpenDialog(parent);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    }
                fileChooser = null;

                //JOptionPane.showMessageDialog(null, "ok");


                return  JSValue.createUndefined();

            }

        });

        SecureRandom random = new SecureRandom();

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/newQuiz.html?" + new BigInteger(130, random));
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
    }
    public static void main(String[] args){
        NewQuizWindow nqw = new NewQuizWindow();
    }
}
