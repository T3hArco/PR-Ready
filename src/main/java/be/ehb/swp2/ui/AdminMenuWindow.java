package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.TokenNotFoundException;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.manager.UserManager;
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
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by domienhennion on 2/01/16.
 */
public class AdminMenuWindow extends JFrame {
    private SessionFactory factory;
    private QuizManager quizManager;
    private UserManager userManager;

    public AdminMenuWindow(SessionFactory factory) throws UserNoPermissionException {
        this.factory = factory;

        if (!PermissionHandler.currentUserHasPermission(factory, UserRole.ADMINISTRATOR))
            throw new UserNoPermissionException();

        this.quizManager = new QuizManager(factory);
        this.userManager = new UserManager(factory);
        this.initComponents();
    }

    public void initComponents() {
        final JFrame parent = this;
        final AtomicReference<User> result = new AtomicReference<User>();
        final Browser browser = new Browser();

        browser.registerFunction("onCreateUser", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("create user");

                String username = args[0].getString();
                String password = args[1].getString();

                try {
                    userManager.addUser(username, password);
                } catch (DuplicateUserException e) {
                    e.printStackTrace();
                }

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onDeleteUser", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("delete user");
                String username = args[0].getString();
                int id;

                try {
                    id = userManager.getUserByUsername(username);
                    userManager.deleteUser(id);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                } catch (TokenNotFoundException e) {
                    e.printStackTrace();
                }

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onEditUser", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("edit user");
                String username = args[0].getString();
                String password = args[1].getString();

                try {
                    int id = userManager.getUserByUsername(username);
                    userManager.updatePassword(id, password);
                    userManager.updateUserName(id, username);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                } catch (TokenNotFoundException e) {
                    e.printStackTrace();
                }

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onDeleteQuiz", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("delete quiz");
                String quiz = args[0].getString();
                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onEditQuiz", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                System.out.println("edit quiz");
                String quiz = args[0].getString();
                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onShowStats", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                System.out.println("show stats");
                String quiz = jsValues[0].getString();
                browser.dispose();
                parent.setVisible(false);
                parent.dispose();
                StatisticMenuWindow smw = new StatisticMenuWindow();
                return JSValue.createUndefined();
            }

        });

        browser.registerFunction("onExit", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                parent.setVisible(false);
                parent.dispose();
                OverviewWindow o = new OverviewWindow(factory);
                return JSValue.createUndefined();
            }

        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/adminWin.html");
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
