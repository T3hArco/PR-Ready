package be.ehb.swp2.ui.statistics;

import be.ehb.swp2.ui.StatisticMenuWindow;
import be.ehb.swp2.util.LineChartData;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by arnaudcoel on 03/01/16.
 */
public class LineChartWindow implements be.ehb.swp2.ui.Window {
    private SessionFactory factory;
    private JFrame parent;
    private JDialog dialog;
    private Browser browser;
    private LineChartData[] dataArr;
    private String title, subtitle, titleLeft;
    private String[] categories;

    public LineChartWindow(SessionFactory factory, LineChartData[] dataArr, String title, String subtitle, String titleLeft, String[] categories) {
        this.factory = factory;
        this.parent = new JFrame();
        this.dialog = new JDialog(parent, "Overview", true);
        this.browser = new Browser();
        this.factory = factory;
        this.dataArr = dataArr;
        this.title = title;
        this.subtitle = subtitle;
        this.titleLeft = titleLeft;
        this.categories = categories;

        this.initComponents();
    }

    public void initComponents() {
        int longestData = 0;
        for (int i = 0; i < dataArr.length; i++) {
            if (dataArr[i].getData().length > longestData) {
                longestData = dataArr[i].getData().length;
            }
        }

        final File temp;
        String absolutePath = null;
        String tempFilePath = null;
        try {
            temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
            absolutePath = temp.getAbsolutePath();
            tempFilePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter html = new PrintWriter(tempFilePath + "/statisticsLine.html");
            html.println(
                    "<!DOCTYPE HTML>\n" +
                            "<html>\n" +
                            "\t<head>\n" +
                            "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                            "\t\t<title>Highcharts Example</title>\n \n" +
                            "\t\t<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js\"></script> \n" +
                            "\t\t<style type=\"text/css\"> \n" +
                            "${demo.css}\n" +
                            "\t\t</style >\n" +
                            "<script type=\"text/javascript\"> \n" +
                            "$(function () { \n" +
                            "$('#container').highcharts({\n" +
                            "title: {\n" +
                            "text: '" + title + "', \n" +
                            "x: -20 //center \n" +
                            "},\n" +
                            "subtitle: {\n" +
                            "text: '" + subtitle + "',\n" +
                            "x: -20\n" +
                            "},\n" +
                            "xAxis: {\n" +
                            "categories: [");
            for (int i = 0; i < categories.length; i++) {
                html.println("'" + categories[i] + "'");
                if (i < categories.length - 1) {
                    html.println(",");
                }
            }


            html.println("] \n},\n" +
                    "yAxis: {\n" +
                    "title: {\n" +
                    "text: '" + titleLeft + "'\n" +
                    "},\n" +
                    "plotLines: [{ \n" +
                    "value: 0,\n" +
                    "width: 1,\n" +
                    "color: '#808080'\n" +
                    "}]\n" +
                    "},\n" +
                    "tooltip: {\n" +
                    "valueSuffix: '/100'\n" +
                    "},\n" +
                    "legend: {\n" +
                    "layout: 'vertical',\n" +
                    "align: 'right',\n" +
                    "verticalAlign: 'middle',\n" +
                    "borderWidth: 0\n" +
                    "},\n" +
                    "series: [{\n");

            for (int i = 0; i < dataArr.length; i++) {
                html.println("name: \"" + dataArr[i].getName() + "\",\n data: [");
                for (int j = 0; j < dataArr[i].getData().length; j++) {
                    html.println(dataArr[i].getData()[j]);
                    if (dataArr[i].getData()[j] != dataArr[i].getData()[dataArr[i].getData().length - 1]) {
                        html.println(",");
                    }
                }
                html.println("]}");
                if (dataArr[i] != dataArr[dataArr.length - 1]) {
                    html.println(", {\n");
                }
            }


            html.println("]\n" +
                    "    });\n" +
                    "});\n" +
                    "\t\t</script>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "<script src=\"http://dtprojecten.ehb.be/~PR-Ready/StatisticsJS/js/highcharts.js\"></script>\n" +
                    "<script src=\"http://dtprojecten.ehb.be/~PR-Ready/StatisticsJS/js/modules/exporting.js\"></script>\n" +
                    "\n" +
                    "<div id=\"container\" style=\"min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto\"></div>\n" +
                    "\n" +
                    "<button onclick=\"exit();\" style=\" margin: auto; padding: 1em; float: right;\">exit</button>\n" +
                    "<script> function exit(){ closeStat() } </script>\n" +
                    "\t</body>\n" +
                    "</html>");
            html.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        browser.registerFunction("closeStat", new BrowserFunction() {
            public JSValue invoke(JSValue... jsValues) {

                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                StatisticMenuWindow smw = new StatisticMenuWindow(factory);

                return JSValue.createUndefined();
            }
        });

        browser.loadURL("file://" + tempFilePath + "/statisticsLine.html");
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
