package be.ehb.swp2.ui;

import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.util.ColumnData;
import be.ehb.swp2.util.LineChartData;
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
import java.util.ArrayList;
import be.ehb.swp2.util.PieChartData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Thomas on 2/01/2016.
 */
public class StatisticMenuWindow {
    private static SessionFactory factory;


    public StatisticMenuWindow(SessionFactory factory){
        this.factory = factory;
        this.initComponents();
    }


    public static void initComponents() {

        final Browser browser = new Browser();
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "QUIZ", true);

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/StatMenuWindow.html?851951951951951");
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
            }
        });


        browser.registerFunction("createPieChart", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                System.out.println("pie");
                PieChartData[] dataArr = new PieChartData[3];
                dataArr[0] = new PieChartData("first", 30);
                dataArr[1] = new PieChartData("second", 50);
                dataArr[2] = new PieChartData("third", 20);
                StatisticWindow.printPie(dataArr, "test");
                return JSValue.createUndefined();
            }
        });


        browser.registerFunction("createLineChart", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                System.out.println("line");
                LineChartData[] lineData = new LineChartData[3];
                String title = "title";
                String subtitle = "subtitle";
                String leftTitle = "title left";
                String[] categories = new String[3];
                categories[0] = "categorie1";
                categories[1] = "categorie2";
                double[] data = new double[2];
                data[0] = 60.00;
                data[1] = 40.00;
                lineData[0] = new LineChartData("line1", data);
                lineData[1] = new LineChartData("line2", data);
                lineData[2] = new LineChartData("line3", data);
                StatisticWindow.printLine(lineData, title, subtitle, leftTitle, categories);
                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("createColumnChart", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                System.out.println("column");
                ColumnData[] dataColumn = new ColumnData[3];
                double[] data = new double[2];
                data[0] = 60.00;
                data[1] = 40.00;
                String title = "title";
                String subtitle = "subtitle";
                dataColumn[0] = new ColumnData("column1", data);
                dataColumn[1] = new ColumnData("column2", data);
                dataColumn[2] = new ColumnData("column3", data);
                StatisticWindow.printColumn(dataColumn, title, subtitle);

                return JSValue.createUndefined();
            }
        });


        browser.registerFunction("onExit", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                System.out.println("exit");
                try {
                    AdminMenuWindow amw = new AdminMenuWindow(factory);
                    System.out.println("exit2");
                } catch (UserNoPermissionException e) {
                    e.printStackTrace();
                }
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
}
