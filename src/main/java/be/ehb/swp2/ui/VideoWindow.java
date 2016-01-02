package be.ehb.swp2.ui;

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
import be.ehb.swp2.entity.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by domienhennion on 3/12/15.
 */


public class VideoWindow implements QuestionWindow {
    private SessionFactory session;
    private String url;
    private Question question;
    private int choice = 1;

    public VideoWindow(SessionFactory session, String url, Question question) {
        this.session = session;
        this.url = url;
        this.question = question;

        this.initComponents();
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
                }
            }
        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/videoFrame.html");
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
                setChoice(1);
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("previousQuestion", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                setChoice(2);
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

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
}
