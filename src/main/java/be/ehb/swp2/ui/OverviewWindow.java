package be.ehb.swp2.ui;

import be.ehb.swp2.entity.Quiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

/**
 * Created by domienhennion on 22/11/15.
 */

public class OverviewWindow {
    private TreeSet<Quiz> quizzen = new TreeSet<Quiz>();

    public OverviewWindow(TreeSet<Quiz> q){
        quizzen = q;
    }
    //een quiz toevoegen
    public Boolean addQuiz(Quiz q){
        if (quizzen.contains(q)){
            return false;
        } else {
            quizzen.add(q);
            return true;
        }
    }
    //een quiz verwijderen
    //voor de implementatie van deze methode is een comparteTo methode in de klasse quiz vereist
    public Boolean removeQuiz(Quiz q){
        if (quizzen.contains(q)){
            quizzen.remove(q);
            return true;
        } else {
            return false;
        }
    }
    public void printGui(){
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
            PrintWriter html = new PrintWriter(tempFilePath + "/overview.html");
            html.println("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>OVERVIEW</title><link rel=\"stylesheet\" href=\"overview.css\"></head><body><div class=\"collection\">");
            int size = quizzen.size();
            for (int i = 0; i< size; i++){
                html.println("<div class=\"quiz\"><div class=\"titel\"><p>"+ quizzen.first().getTitle() +"</p></div><div class=\"desc\"><p>" + quizzen.pollFirst().getDescription() +"</p></div><div class=\"button\"><button>option</button></div></div>");
            }
            html.println("</div></body></html>");
            html.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PrintWriter css;
        try {
            css = new PrintWriter(tempFilePath + "/overview.css");
            css.println("body{ background-color: rgba(46,57,100,1); background: url(\"http://dtprojecten.ehb.be/~PR-Ready/background.jpg\") no-repeat center center fixed; text-align: center; color: whitesmoke; font-family: tahoma; font-size: 15px; } .quiz{ border: 1px solid white; width: 100%; overflow: hidden; white-space: nowrap; display: inline-block; background-color: rgba(0,0,0,0.5); } .quiz .titel{ display: block; float: left; width: 25%; margin: auto; } .quiz .button{ display: block; float: right; width: 25%; margin: auto; } .quiz .desc{ display: block; width: 50%; margin: auto; } .quiz button { -webkit-border-radius: 60; -moz-border-radius: 60; border-radius: 60px; border-color: none; color: #2e3964; background: #ffffff; padding: 8px 15px 8px 15px; } .quiz button:hover { background: #b2d8f0; }");
            css.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*static public void main(String [] args){
        Quiz q = new Quiz("test1", "logo", "Dit is een test");
        Quiz q1 = new Quiz("test2", "logo", "Dit is een test");
        Quiz q2 = new Quiz("test4", "logo", "Dit is een test");
        Quiz q3 = new Quiz("test3", "logo", "Dit is een test");
        TreeSet <Quiz> t = new TreeSet<Quiz>();
        t.add(q);
        t.add(q1);
        t.add(q2);
        t.add(q3);
        OverviewWindow o = new OverviewWindow(t);
        o.printGui();
    }*/

}
