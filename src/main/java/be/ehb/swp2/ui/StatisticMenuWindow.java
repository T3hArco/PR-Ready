package be.ehb.swp2.ui;

import be.ehb.swp2.exception.UserNoPermissionException;
import be.ehb.swp2.ui.statistics.ColumnChartWindow;
import be.ehb.swp2.ui.statistics.LineChartWindow;
import be.ehb.swp2.ui.statistics.PieChartWindow;
import be.ehb.swp2.util.ColumnData;
import be.ehb.swp2.util.LineChartData;
import be.ehb.swp2.util.PieChartData;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;

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
        StatisticMenuWindow.factory = factory;

        initComponents();
    }

    public static void initComponents() {
        final Browser browser = new Browser();
        JFrame parent = new JFrame();
        final JDialog dialog = new JDialog(parent, "QUIZ", true);

        browser.loadURL("http://dtprojecten.ehb.be/~PR-Ready/StatMenuWindow.html?85519519551951951");
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

                PieChartData[] dataArr = new PieChartData[6];
                dataArr[0] = new PieChartData("De grote quiz", 50);
                dataArr[1] = new PieChartData("Test uw IQ!", 1);
                dataArr[2] = new PieChartData("Gestolen rijexamens", 15);
                dataArr[3] = new PieChartData("Win een reis!", 4);
                dataArr[4] = new PieChartData("Test uw kennis!", 10);
                dataArr[5] = new PieChartData("Kan u de verschillen aanwijzen?", 20);
                new PieChartWindow(factory, dataArr, "Populariteit per quiz");

                return JSValue.createUndefined();
            }
        });


        browser.registerFunction("createLineChart", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();

                LineChartData[] lineData = new LineChartData[3];
                String title = "User creation";
                String subtitle = "By month";
                String leftTitle = "Users";
                String[] categories = new String[3];
                categories[0] = "September";
                categories[1] = "Oktober";
                categories[2] = "November";
                double[] data = new double[3];
                data[0] = 10;
                data[1] = 2;
                data[2] = 50;

                lineData[0] = new LineChartData("September", data);
                lineData[1] = new LineChartData("Oktober", data);
                lineData[2] = new LineChartData("November", data);
                new LineChartWindow(factory, lineData, title, subtitle, leftTitle, categories);

                return JSValue.createUndefined();
            }
        });

        browser.registerFunction("createColumnChart", new BrowserFunction() {

            public JSValue invoke(JSValue... jsValues) {
                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                ColumnData[] dataColumn = new ColumnData[1];
                double[] data = new double[3];
                data[0] = 33.6;
                data[1] = 88;
                data[2] = 66;
                String title = "Gemiddelde score per quiz";
                String subtitle = "";
                dataColumn[0] = new ColumnData("Gemiddelde Score", data);

                new ColumnChartWindow(factory, dataColumn, title, subtitle);

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
