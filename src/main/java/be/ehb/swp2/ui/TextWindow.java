package be.ehb.swp2.ui;


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
import java.util.List;


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
                    DOMElement p = document.createElement("p");
                    p.setAttribute("class", "text");
                    DOMNode n = document.createTextNode(question.getText());
                    root.appendChild(p);
                    p.appendChild(n);
                    DOMNode answers = document.findElement(By.id("answers"));
                    if (question.getAnswerType().equals(AnswerType.MULTIPLE_CHOICE)){
                        DOMNode form = document.createElement("form");
                        System.out.println("check2");
                        AnswerManager am = new AnswerManager(session);
                        System.out.println("check3");
                        List<String> answerList = new ArrayList<String>();
                        answerList = am.getAnswerByQuestionId(question.getId());

                        for (String answer : answerList) {
                            DOMElement trueBox = document.createElement("input");
                            trueBox.setAttribute("type", "radio");
                            trueBox.setAttribute("name", "tf");
                            DOMNode dataTrue = document.createTextNode(answer);
                            DOMElement labeltrue = document.createElement("label");
                            labeltrue.appendChild(dataTrue);


                            form.appendChild(trueBox);
                            form.appendChild(labeltrue);
                            DOMElement br = document.createElement("br");
                            form.appendChild(br);
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
                        DOMElement br = document.createElement("br");
                        form.appendChild(br);
                        form.appendChild(labelFalse);
                        form.appendChild(falseBox);//
                        answers.appendChild(form);
                    }

                }
            }
        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/TextFrame.html?85395495155555951959");
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