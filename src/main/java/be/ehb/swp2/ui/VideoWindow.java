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


public class VideoWindow implements Window {
    final private String url;

    VideoWindow(final String url) {
        this.url = url;
    }

    static public void main(String[] arsg) {
        //VideoWindow v = new VideoWindow("mTG2ZBzAZq0");
        //VideoWindow v = new VideoWindow("pk-5aS9G9I4");
        VideoWindow v = new VideoWindow("u1I9ITfzqFs");
        v.printGui();
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

                    String videoUrl = "https://www.youtube.com/embed/" + url + "?rel=0&amp;controls=0&amp;showinfo=0";

                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("video"));
                    DOMElement iframe = document.createElement("iframe");
                    iframe.setAttribute("src", videoUrl);
                    iframe.setAttribute("frameborder", "0");
                    root.appendChild(iframe);
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

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

    }
}
