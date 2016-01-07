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
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by domienhennion on 3/12/15.
 * Modified by arnaudcoel
 */
public class AudioWindow implements QuestionWindow {
    private SessionFactory factory;
    private String url;
    private Question question;
    private int choice;
    private QuizLauncher quizLauncher;

    /**
     * The constructor voor AudioWindow
     *
     * @param url      URL van de question
     * @param question Parent question object
     */
    public AudioWindow(SessionFactory factory, String url, Question question, QuizLauncher quizLauncher) {
        this.factory = factory;
        this.url = url;
        this.question = question;
        this.quizLauncher = quizLauncher;


    }

    /**
     * Initializes the GUI
     */
    public void initComponents() {
        final Browser browser = new Browser();
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "QUIZ", true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {

                    String audioUrl = "http://www.youtube.com/embed/" + url + "?enablejsapi=1&amp;fs=0&amp;rel=0&amp;showinfo=0&amp;modestbranding=1&amp;iv_load_policy=3&amp;controls=0&amp;autoplay=0&amp;loop=0";

                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("audio"));
                    DOMElement iframe = document.createElement("iframe");
                    iframe.setAttribute("width", "250");
                    iframe.setAttribute("height", "250");
                    iframe.setAttribute("src", audioUrl);
                    iframe.setAttribute("onload", "gaf210codes_qTnZ2=new YT.Player(this)");
                    root.appendChild(iframe);
                    DOMNode root2 = document.findElement(By.id("text"));
                    DOMElement p = document.createElement("p");
                    p.setAttribute("class", "text");
                    DOMNode n = document.createTextNode(question.getText());
                    root2.appendChild(p);
                    p.appendChild(n);

                    DOMNode answers = document.findElement(By.id("answers"));
                    if (question.getAnswerType().equals(AnswerType.MULTIPLE_CHOICE)){
                        DOMNode form = document.createElement("form");

                        AnswerManager am = new AnswerManager(factory);
                        List<String> answerList = am.getAnswerByQuestionId(question.getId());

                        for (String answer : answerList) {
                            DOMElement trueBox = document.createElement("input");
                            trueBox.setAttribute("type", "radio");
                            trueBox.setAttribute("name", "tf");
                            DOMNode dataTrue = document.createTextNode(answer);
                            DOMElement labeltrue = document.createElement("label");
                            labeltrue.appendChild(dataTrue);

                            form.appendChild(labeltrue);
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

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/audioFrame.html?85395495195PLOPKOEKENZIJNGEVAARLIJK1959");
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
