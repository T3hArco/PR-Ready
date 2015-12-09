package be.ehb.swp2.ui;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by domienhennion on 3/12/15.
 */
public class AudioWindow implements questionWindow{
    final private String url;

    AudioWindow(final String url) {
        this.url = url;
    }

    static public void main(String[] arsg) {
        //AudioWindow a = new AudioWindow("mTG2ZBzAZq0");
        //AudioWindow a = new AudioWindow("pk-5aS9G9I4");
        AudioWindow a = new AudioWindow("u1I9ITfzqFs");
        a.printGui();
    }

    public void printGui() {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
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

                }
            }
        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/audioFrame.html");
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
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

}
