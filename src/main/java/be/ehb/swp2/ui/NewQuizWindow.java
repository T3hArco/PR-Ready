package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.util.PermissionHandler;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by domienhennion on 8/12/15.
 * Modified by Arnaud..
 */
public class NewQuizWindow extends JFrame implements QuestionWindow, Window {
    private SessionFactory factory;
    private QuizManager quizManager;

    public NewQuizWindow(SessionFactory factory) throws UserNoPermissionException {
        this.factory = factory;

        if (!PermissionHandler.currentUserHasPermission(factory, UserRole.ADMINISTRATOR))
            throw new UserNoPermissionException();

        this.quizManager = new QuizManager(factory);
        this.initComponents();
    }

    public void initComponents() {
        final JFrame parent = this;
        final AtomicReference<User> result = new AtomicReference<User>();
        final Browser browser = new Browser();
        final URL[] filePath = {null};
        final File[] file = {null};

        browser.registerFunction("newFile", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setCurrentDirectory(new File("."));
                jFileChooser.setDialogTitle("Image selection");

                if (jFileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
                    try {
                        file[0] = jFileChooser.getSelectedFile();
                        filePath[0] = new URL("file://" + file[0].getCanonicalPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                return JSValue.createUndefined();
            }

        });

        browser.registerFunction("createQuiz", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("hallo");
                String name = args[0].getString();
                String description = args[1].getString();
                Integer newQuiz = null;

                URL imagePath = null;
                try {
                    newQuiz = quizManager.addQuiz(name, description);
                    quizManager.saveLogo(newQuiz, filePath[0]);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    EditorWindow ew = new EditorWindow(factory, newQuiz);
                } catch (UserNoPermissionException e) {
                    e.printStackTrace();
                }
                browser.dispose();
                parent.setVisible(false);
                parent.dispose();
                return JSValue.createUndefined();
            }
        });

        //SecureRandom random = new SecureRandom();

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/newQuiz.html");
        parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                browser.dispose();
                parent.setVisible(false);
                parent.dispose();
            }
        });

        parent.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        parent.add(new BrowserView(browser), BorderLayout.CENTER);
        parent.setResizable(false);
        parent.setUndecorated(true);
        parent.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        parent.setLocationRelativeTo(parent);
        parent.setVisible(true);
        this.setVisible(true);
    }
}
