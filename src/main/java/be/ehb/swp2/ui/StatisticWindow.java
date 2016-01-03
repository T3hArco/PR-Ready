package be.ehb.swp2.ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;

import be.ehb.swp2.util.ColumnData;
import be.ehb.swp2.util.LineChartData;
import be.ehb.swp2.util.PieChartData;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.hibernate.SessionFactory;
import org.jboss.jandex.Main;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Chris on 26/11/2015.
 */
public class StatisticWindow {
    static final JFrame parent = new JFrame();
    static final JDialog dialog = new JDialog(parent, "Overview", true);
    static final Browser browser = new Browser();
    private static SessionFactory session;

    public StatisticWindow(SessionFactory session){
        this.session = session;
    }

    public static void printPie(PieChartData[] DataArr, String title){
        final File temp;
        String absolutePath = null;
        String tempFilePath = null;


        try {
            temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
            absolutePath = temp.getAbsolutePath();
            tempFilePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
            System.out.println("Temp file path : " + tempFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
                    "        },\n"  +
                    "        title: {\n" +
                    "            text: '" + title + "'\n" +
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
            for (int i = 0; i < DataArr.length; i++) {
                html.println( "{ name: \"" + DataArr[i].getName() +"\", y: " + DataArr[i].getPercentage() + "}");
                if (i != DataArr.length-1){
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        browser.registerFunction("closeStat", new BrowserFunction() {
            public JSValue invoke(JSValue... jsValues) {

                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                StatisticMenuWindow smw = new StatisticMenuWindow(session);

                return JSValue.createUndefined();
            }
        });
        System.out.println("load");
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

    public static void printLine(LineChartData[] DataArr, String title, String subtitle, String titleLeft, String[] categories){
        int longestData = 0;
        for (int i = 0; i<DataArr.length; i++){
            if(DataArr[i].getData().length > longestData){
                longestData = DataArr[i].getData().length;
            }
        }

        final File temp;
        String absolutePath = null;
        String tempFilePath = null;
        try {
            temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
            absolutePath = temp.getAbsolutePath();
            tempFilePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
            System.out.println("Temp file path : " + tempFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
                            "text: '" + subtitle +"',\n" +
                            "x: -20\n" +
                            "},\n" +
                            "xAxis: {\n" +
                            "categories: [");
            for (int i=0;i<categories.length;i++){
                html.println("'"+categories[i]+"'");
                if(i<categories.length-1){
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
            for (int i = 0; i < DataArr.length; i++) {
                html.println( "name: \"" + DataArr[i].getName() +"\",\n data: [");
                for (int j=0; j<DataArr[i].getData().length; j++){
                    html.println(DataArr[i].getData()[j]);
                    if(DataArr[i].getData()[j] != DataArr[i].getData()[DataArr[i].getData().length-1]){
                        html.println(",");
                    }
                }
                html.println("]}");
                if(DataArr[i] != DataArr[DataArr.length-1]){
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
                StatisticMenuWindow smw = new StatisticMenuWindow(session);

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

    public static void printColumn(ColumnData[] DataArr, String title, String subtitle){

        final File temp;
        String absolutePath = null;
        String tempFilePath = null;
        try {
            temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
            absolutePath = temp.getAbsolutePath();
            tempFilePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
            System.out.println("Temp file path : " + tempFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            PrintWriter html = new PrintWriter(tempFilePath + "/statisticsColumn.html");
            html.println(
                    "<!DOCTYPE HTML>\n"+
                            "\t<html>\n" +
                            "\t\t<head>\n" +
                            "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> \n"+
                            "\t\t<title>Highcharts Example</title> \n" +
                            "\t\t<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js\"></script> \n" +
                            "\t\t<style type=\"text/css\"> \n" +
                            "#container, #sliders { \n" +
                            "min-width: 310px; \n" +
                            "max-width: 800px; \n" +
                            "margin: 0 auto;\n"+
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
                            "enabled: true,\n"+
                            "alpha: 15,\n" +
                            "beta: 15,\n" +
                            "depth: 50,\n" +
                            "viewDistance: 25\n"+
                            "}\n" +
                            "},\n" +
                            "title: {\n" +
                            "text: '" + title + "'\n" +
                            "},\n" +
                            "subtitle: {\n" +
                            "text: '"+ subtitle +"'\n"+
                            "},\n" +
                            "plotOptions: {\n" +
                            "column: {\n" +
                            "depth: 25\n" +
                            "}\n" +
                            "},\n" +
                            "series: [{\n");
            for (int i = 0; i < DataArr.length; i++) {
                html.println( "name: \"" + DataArr[i].getName() +"\",\n data: [");
                for (int j=0; j<DataArr[i].getData().length; j++){
                    html.println(DataArr[i].getData()[j]);
                    if(DataArr[i].getData()[j] != DataArr[i].getData()[DataArr[i].getData().length-1]){
                        html.println(",");
                    }
                }
                html.println("]}");
                if(DataArr[i] != DataArr[DataArr.length-1]){
                    html.println(", {\n");
                }
            }


            html.println(
                    "]});\n" +
                            "function showValues() {\n" +
                            "$('#R0-value').html(chart.options.chart.options3d.alpha);\n" +
                            "$('#R1-value').html(chart.options.chart.options3d.beta);\n" +
                            "}\n" +
                            "$('#R0').on('change', function () {\n"+
                            "chart.options.chart.options3d.alpha = this.value; \n" +
                            "showValues();\n" +
                            "chart.redraw(false);\n"+
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        browser.registerFunction("closeStat", new BrowserFunction() {
            public JSValue invoke(JSValue... jsValues) {

                browser.dispose();
                dialog.setVisible(false);
                dialog.dispose();
                StatisticMenuWindow smw = new StatisticMenuWindow(session);

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