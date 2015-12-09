package be.ehb.swp2.ui;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
/**
 * Created by Thomas on 3/12/2015.
 */
public class ImageWindow implements questionWindow{
    final private String url;
    final private String question;

    public ImageWindow(final String url, final String question){
        this.url = url;
        this.question = question;
    }
    public void printGui(){
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "QUIZ", true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {

                    String imageURL = url;
/* fsfds*/
                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("img"));
                    DOMElement img = document.createElement("img");
                    img.setAttribute("src", imageURL);
                    root.appendChild(img);
                    DOMNode root2 = document.findElement(By.id("text"));
                    DOMElement p = document.createElement("p");
                    p.setAttribute("class", "text");
                    DOMNode n = document.createTextNode(question);
                    root2.appendChild(p);
                    p.appendChild(n);

                }
            }
        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/ImageFrame.html");
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

         browser.registerFunction("nextQuestion", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                return null;
            }



        });

    }

    static public void main(String[] args){
        //VideoWindow v = new VideoWindow("mTG2ZBzAZq0");
        //VideoWindow v = new VideoWindow("pk-5aS9G9I4");
        ImageWindow i = new ImageWindow("http://s3.standaardcdn.be/Assets/Images_Upload/2011/10/03/fktwitter2.jpg?scale=both&format=jpg","wie ben ik");
        i.printGui();
    }
}
