package be.ehb.swp2.ui.statistics;

import be.ehb.swp2.ui.StatisticMenuWindow;
import be.ehb.swp2.util.ColumnData;
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
public class ColumnChartWindow implements be.ehb.swp2.ui.Window {
    private SessionFactory factory;
    private JFrame parent;
    private JDialog dialog;
    private Browser browser;
    private ColumnData[] dataArr;
    private String title, subtitle;

    public ColumnChartWindow(SessionFactory factory, ColumnData[] dataArr, String title, String subtitle) {
        this.factory = factory;
        this.parent = new JFrame();
        this.dialog = new JDialog(parent, "Overview", true);
        this.browser = new Browser();
        this.dataArr = dataArr;
        this.title = title;
        this.subtitle = subtitle;

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
            PrintWriter html = new PrintWriter(tempFilePath + "/statisticsColumn.html");
            html.println(
                    "<!DOCTYPE HTML>\n" +
                            "\t<html>\n" +
                            "\t\t<head>\n" +
                            "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> \n" +
                            "\t\t<title>Highcharts Example</title> \n" +
                            "\t\t<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js\"></script> \n" +
                            "\t\t<style type=\"text/css\"> \n" +
                            "#container, #sliders { \n" +
                            "min-width: 310px; \n" +
                            "max-width: 800px; \n" +
                            "margin: 0 auto;\n" +
                            "}\n" +
                            "#container {\n" +
                            "height: 400px; \n" +
                            "}\n" +
                            "</style>\n" +
                            "<script type=\"text/javascript\">\n" +
                            "$(function () {\n" +
                            "var chart = new Highcharts.Chart({\n" +
                            "chart: {\n" +
                            "renderTo: 'container',\n" +
                            "type: 'column',\n" +
                            "margin: 75,\n" +
                            "options3d: {\n" +
                            "enabled: true,\n" +
                            "alpha: 15,\n" +
                            "beta: 15,\n" +
                            "depth: 50,\n" +
                            "viewDistance: 25\n" +
                            "}\n" +
                            "},\n" +
                            "title: {\n" +
                            "text: '" + title + "'\n" +
                            "},\n" +
                            "subtitle: {\n" +
                            "text: '" + subtitle + "'\n" +
                            "},\n" +
                            "plotOptions: {\n" +
                            "column: {\n" +
                            "depth: 25\n" +
                            "}\n" +
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


            html.println(
                    "]});\n" +
                            "function showValues() {\n" +
                            "$('#R0-value').html(chart.options.chart.options3d.alpha);\n" +
                            "$('#R1-value').html(chart.options.chart.options3d.beta);\n" +
                            "}\n" +
                            "$('#R0').on('change', function () {\n" +
                            "chart.options.chart.options3d.alpha = this.value; \n" +
                            "showValues();\n" +
                            "chart.redraw(false);\n" +
                            "});\n" +
                            "$('#R1').on('change', function () {\n" +
                            "chart.options.chart.options3d.beta = this.value;\n" +
                            "showValues();\n" +
                            "chart.redraw(false);\n" +
                            "});\n" +
                            "showValues();\n" +
                            "});\n" +
                            "</script>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<script src=\"http://dtprojecten.ehb.be/~PR-Ready/StatisticsJS/js/highcharts.js\"></script>\n" +
                            "<script src=\"http://dtprojecten.ehb.be/~PR-Ready/StatisticsJS/js/highcharts-3d.js\"></script>\n" +
                            "<script src=\"http://dtprojecten.ehb.be/~PR-Ready/StatisticsJS/js/modules/exporting.js\"></script>\n" +
                            "<div id=\"container\"></div>\n" +
                            "<div id=\"sliders\">\n" +
                            "<table>\n" +
                            "<tr><td>Alpha Angle</td><td><input id=\"R0\" type=\"range\" min=\"0\" max=\"45\" value=\"15\"/> <span id=\"R0-value\" class=\"value\"></span></td></tr>\n" +
                            "<tr><td>Beta Angle</td><td><input id=\"R1\" type=\"range\" min=\"0\" max=\"45\" value=\"15\"/> <span id=\"R1-value\" class=\"value\"></span></td></tr>\n" +
                            "</table>\n" +
                            "<button onclick=\"exit();\" style=\" margin: auto; padding: 1em; float: right;\">exit</button>\n" +
                            "<script> function exit(){ closeStat() } </script>\n" +
                            "</div>\n" +
                            "</body>\n" +
                            "</html>\n" +
                            "\n");
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


        browser.loadURL("file://" + tempFilePath + "/statisticsColumn.html");
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
