package be.ehb.swp2.ui;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by domienhennion on 12/12/15.
 */
public class LoadingWindow implements Window {
    /**
     * Default cons
     */
    public LoadingWindow() {
        this.initComponents();
    }

    /**
     * Inititialize the GUI
     */
    public void initComponents() {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "LOADING", true);

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/loadingFrame.html?9867987687");

        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setResizable(false);
        dialog.setUndecorated(true);
        dialog.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}
