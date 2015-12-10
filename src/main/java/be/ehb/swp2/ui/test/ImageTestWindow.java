package be.ehb.swp2.ui.test;

import be.ehb.swp2.exception.BadFileException;
import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.ui.Window;
import be.ehb.swp2.util.ImageHandler;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by arnaudcoel on 10/12/15.
 */
public class ImageTestWindow extends JFrame implements Window {
    private SessionFactory factory;
    private QuizManager quizManager;

    public ImageTestWindow(SessionFactory factory) {
        this.factory = factory;
        this.quizManager = new QuizManager(factory);

        this.initComponents();
    }

    public void initComponents() {
        final JFrame parent = this;
        final Browser browser = new Browser();
        URL filePath = null;
        File file = null;

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("."));
        jFileChooser.setDialogTitle("Test image selection");

        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                file = jFileChooser.getSelectedFile();
                filePath = new URL("file://" + file.getCanonicalPath());
                System.out.println(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String base64 = quizManager.getQuizById(1).getLogo();
                String base642 = ImageHandler.toBase64(filePath);

                browser.loadHTML("<img src=\"data:image/png;base64," + base64 + "\"/>" +
                        "<img src=\"data:image/png;base64," + base642 + "\"/>");
            } catch (QuizNotFoundException e) {
                e.printStackTrace();
            } catch (BadFileException e) {
                e.printStackTrace();
            }

        }


        parent.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                browser.dispose();
                parent.setVisible(false);
                parent.dispose();
            }
        });

        parent.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        parent.add(new BrowserView(browser), BorderLayout.CENTER);
        parent.setSize(new Dimension(600, 400));
        parent.setVisible(true);
    }

}
