package be.ehb.swp2.ui.statistics;

import be.ehb.swp2.ui.StatisticMenuWindow;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by arnaudcoel on 03/01/16.
 */
public class PieChartWindow implements be.ehb.swp2.ui.Window {
    private SessionFactory factory;
    private JFrame parent;
    private JDialog dialog;
    private Browser browser;
    private PieChartData[] dataArr;
    private String title;

    public PieChartWindow(SessionFactory factory, PieChartData[] dataArr, String title) {
        this.factory = factory;
        this.parent = new JFrame();
        this.dialog = new JDialog(parent, "Overview", true);
        this.browser = new Browser();
        this.dataArr = dataArr;
        this.title = title;

        this.initComponents();
    }

    public void initComponents() {
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
            PrintWriter html = new PrintWriter(tempFilePath + "/statisticsPie.html");
            html.println("<!DOCTYPE HTML>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                    "\t\t<title>Highcharts Example</title>\n" +
                    "\n" +
                    "\t\t<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js\"></script>\n" +
                    "\t\t<style type=\"text/css\">\n" +
                    "${demo.css}\n" +
                    "\t\t</style>\n" +
                    "\t\t<script type=\"text/javascript\">\n" +
                    "$(function () {\n" +
                    "    $('#container').highcharts({\n" +
                    "        chart: {\n" +
                    "            plotBackgroundColor: null,\n" +
                    "            plotBorderWidth: null,\n" +
                    "            plotShadow: false,\n" +
                    "            type: 'pie'\n" +
                    "        },\n" +
                    "        title: {\n" +
                    "            text: '" + this.title + "'\n" +
                    "        },\n" +
                    "        tooltip: {\n" +
                    "            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'\n" +
                    "        },\n" +
                    "        plotOptions: {\n" +
                    "            pie: {\n" +
                    "                allowPointSelect: true,\n" +
                    "                cursor: 'pointer',\n" +
                    "                dataLabels: {\n" +
                    "                    enabled: true,\n" +
                    "                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',\n" +
                    "                    style: {\n" +
                    "                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            }\n" +
                    "        },\n" +
                    "        series: [{ \n" +
                    " name:\"Percentage\", \n" +
                    " colorByPoint: true, \n" +
                    " data:[");
            for (int i = 0; i < dataArr.length; i++) {
                html.println("{ name: \"" + dataArr[i].getName() + "\", y: " + dataArr[i].getPercentage() + "}");
                if (i != dataArr.length - 1) {
                    html.println(", ");
                }
            }

            html.println("]\n" +
                    "        }]\n" +
                    "    });\n" +
                    "});\n" +
                    "\t\t</script>\n" +
                    "\t</head>\n" +
                    "\t<body>\n" +
                    "<script src=\"http://dtprojecten.ehb.be/~PR-Ready/StatisticsJS/js/highcharts.js\"></script>\n" +
                    "<script src=\"http://dtprojecten.ehb.be/~PR-Ready/StatisticsJS/js/modules/exporting.js\"></script>\n" +
                    "\n" +
                    "<div id=\"container\" style=\"min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto\">\n" +
                    "</div>\n" +
                    "\n" +
                    "<button onclick=\"exit();\" style=\" margin: auto; padding: 1em; float: right;\">exit</button>\n" +
                    "<script> function exit(){ closeStat() } </script>\n" +
                    "\t</body>\n" +
                    "</html>");
            html.close();
        } catch (FileNotFoundException e) {
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

        browser.loadURL("file://" + tempFilePath + "/statisticsPie.html");
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
