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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by domienhennion on 2/01/16.
 */
public class AdminMenuWindow extends JFrame {
    private SessionFactory factory;
    private QuizManager quizManager;

    public AdminMenuWindow(SessionFactory factory) throws UserNoPermissionException {
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
