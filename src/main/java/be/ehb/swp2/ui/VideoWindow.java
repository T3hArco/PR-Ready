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
import java.util.*;

/**
 * Created by domienhennion on 3/12/15.
 */


public class VideoWindow implements QuestionWindow {
    private SessionFactory session;
    private String url;
    private Question question;
    private int choice;
    private QuizLauncher quizLauncher;

    public VideoWindow(SessionFactory session, String url, Question question, QuizLauncher quizLauncher) {
        this.session = session;
        this.url = url;
        this.question = question;
        this.quizLauncher = quizLauncher;
    }

    public void initComponents() {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "QUIZ", true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    String videoUrl = "https://www.youtube.com/embed/" + url + "?rel=0&amp;controls=0&amp;showinfo=0";
                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("video"));
                    DOMElement iframe = document.createElement("iframe");
                    iframe.setAttribute("src", videoUrl);
                    iframe.setAttribute("frameborder", "0");
                    root.appendChild(iframe);
                    DOMNode root2 = document.findElement(By.id("text"));
                    DOMElement p = document.createElement("p");
                    p.setAttribute("class", "text");
                    DOMNode n = document.createTextNode(question.getText());
                    root2.appendChild(p);
                    p.appendChild(n);
                    DOMNode answers = document.findElement(By.id("answers"));
                    if (question.getAnswerType().equals(AnswerType.MULTIPLE_CHOICE)){


                        AnswerManager am = new AnswerManager(session);
                        java.util.List<String> ans = am.getAnswersByQuestionId(question.getId());

                        DOMNode form = document.createElement("form");
                        for (String str : ans){
                            DOMElement answer = document.createElement("input");
                            answer.setAttribute("type", "radio");
                            answer.setAttribute("name", "mc");
                            DOMNode dataTrue = document.createTextNode(str);
                            DOMElement labeltrue = document.createElement("label");
                            labeltrue.appendChild(dataTrue);
                            form.appendChild(labeltrue);
                            form.appendChild(answer);
                            DOMElement br = document.createElement("br");
                            form.appendChild(br);//

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
                        form.appendChild(trueBox);//
                        DOMElement br = document.createElement("br");
                        form.appendChild(br);
                        form.appendChild(labelFalse);
                        form.appendChild(falseBox);//
                        answers.appendChild(form);
                    }
                }
            }
        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/videoFrame.html?853954951951959");
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

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
}
