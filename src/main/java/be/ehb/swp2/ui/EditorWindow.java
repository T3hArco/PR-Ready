package be.ehb.swp2.ui;

import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.util.PermissionHandler;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by domienhennion on 10/12/15.
 */
public class EditorWindow extends JFrame implements Window {
    private SessionFactory factory;
    private QuizManager quizManager;
    private Integer quizId;

    public EditorWindow(SessionFactory factory, Integer quizId) throws UserNoPermissionException {
        this.factory = factory;

        if (!PermissionHandler.currentUserHasPermission(factory, UserRole.ADMINISTRATOR))
            throw new UserNoPermissionException();

        this.quizManager = new QuizManager(factory);
        this.quizId = quizId;

        this.initComponents();
    }

    public void initComponents() {
        final Browser browser = new Browser();
        JFrame parent = this;
        final JDialog dialog = new JDialog(parent, "Editor", true);

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/editor/editor.html");

        browser.registerFunction("saveQuestionToJava", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                return  JSValue.createUndefined();
            }

        });

        browser.registerFunction("saveAnswerToJava", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                return JSValue.createUndefined();
            }

        });

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                String base64 = null;

                try {
                    base64 = quizManager.getQuizById(quizId).getLogo();
                } catch (QuizNotFoundException e) {
                    e.printStackTrace();
                }

                DOMDocument document = event.getBrowser().getDocument();
                DOMNode logoDiv = document.findElement(By.id("demo"));

                DOMElement logo = document.createElement("img");
                logo.setAttribute("src", "data:image/png;base64," + base64);

                logoDiv.appendChild(logo);
            }
        });

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        dialog.setAlwaysOnTop(true);
    }
}
