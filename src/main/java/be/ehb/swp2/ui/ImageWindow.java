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
 * Created by Thomas on 3/12/2015.
 */
public class ImageWindow {
    final private String url;

    ImageWindow(final String url) {
        this.url = url;
    }

    static public void main(String[] arsg) {
        //VideoWindow v = new VideoWindow("mTG2ZBzAZq0");
        //VideoWindow v = new VideoWindow("pk-5aS9G9I4");
        ImageWindow i = new ImageWindow("u1I9ITfzqFs");
        i.printGui();
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

                    String imageURL = "here comes imge url";

                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("question_field"));
                    DOMElement img = document.createElement("div");
                    img.setAttribute("class", "question_img");
                    root.appendChild(img);

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
