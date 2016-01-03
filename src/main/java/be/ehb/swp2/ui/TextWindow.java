package be.ehb.swp2.ui;


import be.ehb.swp2.entity.QuizLauncher;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;
import be.ehb.swp2.entity.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Created by Thomas on 3/12/2015.
 */
public class TextWindow implements QuestionWindow {
    private SessionFactory session;
    private Question question;
    private int choice;
    private QuizLauncher quizLauncher;

    /**
     * Constructor
     *
     * @param session  question factory
     * @param question question parent
     */
    public TextWindow(SessionFactory session, Question question, QuizLauncher quizLauncher) {
        this.session = session;
        this.question = question;
        this.quizLauncher = quizLauncher;
    }

    /**
     * Initialize the GUI
     */
    public void initComponents() {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "QUIZ", true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("text"));
                    DOMNode n = document.createTextNode(question.getText());
                    root.appendChild(n);
                }
            }
        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/TextFrame.html");
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
            }
        });



        browser.registerFunction("nextQuestion", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                System.out.println("next");
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                quizLauncher.setIncrement(quizLauncher.getIncrement() + 1);
                quizLauncher.windowChoice();
                return JSValue.createUndefined();
            }


        });

        browser.registerFunction("previousQuestion", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                System.out.println("back");
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                quizLauncher.setIncrement(quizLauncher.getIncrement() + 1);
                quizLauncher.windowChoice();
                return JSValue.createUndefined();
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

    /**
     * Getter for choice
     *
     * @return integer
     */
    public int getChoice() {
        return choice;
    }

    /**
     * Sets the choice
     *
     * @param choice integer
     */
    public void setChoice(int choice) {
        this.choice = choice;
    }
}