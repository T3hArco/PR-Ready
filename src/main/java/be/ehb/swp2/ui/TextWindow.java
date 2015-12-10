package be.ehb.swp2.ui;



        import java.awt.BorderLayout;
        import java.awt.GraphicsEnvironment;
        import java.awt.event.WindowAdapter;
        import java.awt.event.WindowEvent;

        import javax.swing.JDialog;
        import javax.swing.JFrame;
        import javax.swing.WindowConstants;

        import be.ehb.swp2.entity.*;
        import be.ehb.swp2.entity.Question;
        import com.teamdev.jxbrowser.chromium.Browser;
        import com.teamdev.jxbrowser.chromium.JSValue;
        import com.teamdev.jxbrowser.chromium.dom.By;
        import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
        import com.teamdev.jxbrowser.chromium.dom.DOMElement;
        import com.teamdev.jxbrowser.chromium.dom.DOMNode;
        import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
        import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
        import com.teamdev.jxbrowser.chromium.swing.BrowserView;
        import com.teamdev.jxbrowser.chromium.BrowserFunction;



/**
 * Created by Thomas on 3/12/2015.
 */
public class TextWindow implements questionWindow{
    private Question question;
    private int choice = 1;

    public TextWindow(Question question){
        this.question = question;

    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
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



                    DOMDocument document = event.getBrowser().getDocument();
                    DOMNode root = document.findElement(By.id("text"));
                    DOMNode n = document.createTextNode(question.getText());
                    root.appendChild(n);


                }
            }
        });

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/question/TextFrame.html");
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setChoice(1);
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
                return  JSValue.createUndefined();
            }



        });

        browser.registerFunction("previousQuestion", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                setChoice(2);
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                return  JSValue.createUndefined();
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




    static public void main(String[] args){
        //VideoWindow v = new VideoWindow("mTG2ZBzAZq0");
        //VideoWindow v = new VideoWindow("pk-5aS9G9I4");
        //TextWindow t = new TextWindow();
        //t.printGui();
    }
}