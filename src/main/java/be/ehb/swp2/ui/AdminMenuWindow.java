package be.ehb.swp2.ui;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.*;
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
    private JFrame thisFrame;

    public AdminMenuWindow(SessionFactory factory) throws UserNoPermissionException {
        this.factory = factory;
        this.thisFrame = this;

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
                String username = args[0].getString();
                String password = args[1].getString();

                try {
                    if (username.equals("") || password.equals(""))
                        throw new InternalErrorException();

                    userManager.addUser(username, password);

                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " werd aangemaakt!", "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                } catch (DuplicateUserException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " bestaat al!", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                } catch (InternalErrorException e) {
                    JOptionPane.showMessageDialog(thisFrame, "Gelieve correcte gegevens in te geven", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onDeleteUser", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                String username = args[0].getString();
                int id;

                try {
                    if (username.equals(""))
                        throw new InternalErrorException();

                    id = userManager.getUserByUsername(username);
                    userManager.setUserState(id, false);

                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " werd verwijderd!", "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " kon niet gevonden worden!", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                } catch (TokenNotFoundException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " kon niet gevonden worden! (tokenized)", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                } catch (InternalErrorException e) {
                    JOptionPane.showMessageDialog(thisFrame, "Gelieve correcte gegevens in te geven", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onEditUser", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                String username = args[0].getString();
                String password = args[1].getString();

                try {
                    if (username.equals("") || password.equals(""))
                        throw new InternalErrorException();

                    int id = userManager.getUserByUsername(username);
                    userManager.updatePassword(id, password);
                    userManager.updateUserName(id, username);

                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " werd bewerkt!", "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " kon niet gevonden worden!", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                } catch (TokenNotFoundException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(thisFrame, "Gebruiker met naam " + username + " kon niet gevonden worden! (tokenized)", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                } catch (InternalErrorException e) {
                    JOptionPane.showMessageDialog(thisFrame, "Gelieve correcte gegevens in te geven", "PR-Ready", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onDeleteQuiz", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                String quiz = args[0].getString();
                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onEditQuiz", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                String quiz = args[0].getString();
                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("onShowStats", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                String quiz = jsValues[0].getString();
                browser.dispose();
                parent.setVisible(false);
                parent.dispose();
                StatisticMenuWindow smw = new StatisticMenuWindow(factory);
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
