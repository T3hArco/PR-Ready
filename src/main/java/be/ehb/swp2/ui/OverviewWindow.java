package be.ehb.swp2.ui;

import be.ehb.swp2.entity.Quiz;
import be.ehb.swp2.entity.QuizLauncher;
import be.ehb.swp2.entity.UserRole;
import be.ehb.swp2.exception.DuplicateQuizException;
import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.util.PermissionHandler;
import be.ehb.swp2.util.SecurityHandler;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

/**
 * Created by domienhennion on 22/11/15.
 * Bijgewerkt door arnaudcoel 22/11/15.
 */

public class OverviewWindow implements Window {
    private TreeSet<Quiz> quizSet;
    private SessionFactory factory;
    private QuizManager quizManager;
    private UserManager userManager;

    /**
     * Constructor voor Overviewwindow
     *
     * @param factory de SQL sessie
     */
    public OverviewWindow(SessionFactory factory) {
        this.factory = factory;
        this.quizSet = new TreeSet<Quiz>();
        this.quizManager = new QuizManager(factory);

        this.initComponents();
    }

    public void initComponents() {
        quizSet.addAll(quizManager.getQuizzes());
        final File temp;
        String absolutePath = null;
        String tempFilePath = null;
        try {
            temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
            absolutePath = temp.getAbsolutePath();
            tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
            //System.out.println("Temp file path : " + tempFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter html = new PrintWriter(tempFilePath + "/overview.html");
            html.println("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>OVERVIEW</title><link rel=\"stylesheet\" href=\"overview.css\"></head><body><div class=\"collection\">");
            int size = quizSet.size();
            for (int i = 0; i < size; i++) {
                html.println("<div class=\"quiz\"><div class=\"titel\"><p>" + SecurityHandler.stripTags(quizSet.first().getTitle()) + "</p></div><div class=\"desc\"><p>" + SecurityHandler.stripTags(quizSet.pollFirst().getDescription()) + "</p></div><div class=\"button\"><button onclick=\"launchQ();\">launch</button></div></div>");
            }
            html.println("</div><button onclick=\"launchE();\" class=\"add\">add</button> <script>function launchQ(){ launchQuiz(); } function launchE(){ launchEditor(); } </script></body></html>");

            if (PermissionHandler.currentUserHasPermission(factory, UserRole.ADMINISTRATOR))
                html.println("<button onclick='launchE()'>Add Quiz (Admin)</button>");

            html.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PrintWriter css = null;
        try {
            css = new PrintWriter(tempFilePath + "/overview.css");
            css.println("body{ background-color: rgba(46,57,100,1); background: url(\"http://dtprojecten.ehb.be/~PR-Ready/background.jpg\") no-repeat center center fixed; text-align: center; color: whitesmoke; font-family: tahoma; font-size: 15px; } .quiz{ border: 1px solid white; width: 100%; overflow: hidden; white-space: nowrap; display: inline-block; background-color: rgba(0,0,0,0.5); } .quiz .titel{ display: block; float: left; width: 25%; margin: auto; } .quiz .button{ display: block; float: right; width: 25%; margin: auto; } .quiz .desc{ display: block; width: 50%; margin: auto; } .quiz button, .add { -webkit-border-radius: 60; -moz-border-radius: 60; border-radius: 60px; border-color: none; color: #2e3964; background: #ffffff; padding: 8px 15px 8px 15px; } .quiz button:hover, .add:hover { background: #b2d8f0; } .add { float: right; margin: 1%; margin-right: 3%; }");
            css.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "Overview", true);
        final Browser browser = new Browser();

        browser.loadURL("file://" + tempFilePath + "/overview.html");
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        browser.registerFunction("launchQuiz", new BrowserFunction() {
            public JSValue invoke(JSValue... jsValues) {
                QuizLauncher ql = new QuizLauncher();
                ql.launch();
                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("launchEditor", new BrowserFunction() {
            public JSValue invoke(JSValue... jsValues) {
                try {
                    NewQuizWindow nqw = new NewQuizWindow(factory);
                } catch (UserNoPermissionException e) {
                    e.printStackTrace();
                }

                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();

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
     * Adds a quiz to the list
     *
     * @param q quiz in question
     * @throws DuplicateQuizException if a duplicate was made
     */
    public void addQuiz(Quiz q) throws DuplicateQuizException {
        if (quizSet.contains(q))
            throw new DuplicateQuizException();

        quizSet.add(q);
    }

    /**
     * Removes a quiz from the list
     *
     * @param q quiz in question
     * @throws QuizNotFoundException if the quiz was not found in the list
     */
    public void removeQuiz(Quiz q) throws QuizNotFoundException {
        if (!quizSet.contains(q))
            throw new QuizNotFoundException();

        quizSet.remove(q);
    }
}
