package be.ehb.swp2.ui;

import be.ehb.swp2.entity.Answer;
import be.ehb.swp2.entity.AnswerType;
import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuizLauncher;
import be.ehb.swp2.manager.AnswerManager;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Thomas on 3/12/2015.
 */
public class ImageWindow implements QuestionWindow {
    private SessionFactory session;
    private String url;
    private Question question;
    private int choice;
    private QuizLauncher quizLauncher;

    /**
     * The constructor for ImageWindow
     *
     * @param session  the current session
     * @param url      the URL of the question
     * @param question
     */
    public ImageWindow(SessionFactory session, String url, Question question, QuizLauncher quizLauncher) {
        this.session = session;
        this.url = url;
        this.question = question;
        this.quizLauncher = quizLauncher;
    }

    /**
     * Initialize the GUI
     */
    public void initComponents() {
        final Browser browser = new Browser();
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "QUIZ", true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    String imageURL = url;
                    System.out.println(url);
                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("img"));
                    DOMElement img = document.createElement("img");
                    img.setAttribute("src", imageURL);
                    root.appendChild(img);
                    DOMNode root2 = document.findElement(By.id("text"));
                    DOMElement p = document.createElement("p");
                    p.setAttribute("class", "text");
                    DOMNode n = document.createTextNode(question.getText());
                    root2.appendChild(p);
                    p.appendChild(n);
                    DOMNode answers = document.findElement(By.id("answers"));
                    if (question.getAnswerType().equals(AnswerType.MULTIPLE_CHOICE)){
                        DOMNode form = document.createElement("form");

                        AnswerManager answerManager = new AnswerManager(session);
                        ArrayList<Answer> answerList = new ArrayList<Answer>(answerManager.getAnswersByQuestionId(question.getId()));

                        for (Answer answer : answerList) {
                            DOMElement trueBox = document.createElement("input");
                            trueBox.setAttribute("type", "radio");
                            trueBox.setAttribute("name", "tf");
                            DOMNode dataTrue = document.createTextNode(answer.getText());
                            DOMElement labeltrue = document.createElement("label");
                            labeltrue.appendChild(dataTrue);

                            form.appendChild(labeltrue);
                            form.appendChild(trueBox);
                        }

                        answers.appendChild(form);

                    }
                    if (question.getAnswerType().equals(AnswerType.TRUE_FALSE)) {
                        DOMNode form = document.createElement("form");
                        DOMElement trueBox = document.createElement("input");
                        trueBox.setAttribute("type", "radio");
                        trueBox.setAttribute("name", "tf");
                        DOMNode dataTrue = document.createTextNode("true");
                        DOMElement labeltrue = document.createElement("label");
                        labeltrue.appendChild(dataTrue);
                        DOMElement falseBox = document.createElement("input");
                        DOMNode dataFalse = document.createTextNode("false");
                        DOMElement labelFalse = document.createElement("label");
                        labelFalse.appendChild(dataFalse);
                        falseBox.setAttribute("type", "radio");
                        falseBox.setAttribute("name", "tf");
                        form.appendChild(labeltrue);
                        form.appendChild(trueBox);
                        form.appendChild(labelFalse);
                        form.appendChild(falseBox);//
                        answers.appendChild(form);
                    }

                }

            }
        });


        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/ImageFrame.html?853954955521951959");
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
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                quizLauncher.setIncrement(quizLauncher.getIncrement() - 1);
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
     * Getter for the choice
     *
     * @return integer
     */
    public int getChoice() {
        return choice;
    }

    /**
     * sets the coice
     *
     * @param choice integer
     */
    public void setChoice(int choice) {
        this.choice = choice;
    }
}
