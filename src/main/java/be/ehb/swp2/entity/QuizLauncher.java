package be.ehb.swp2.entity;

import be.ehb.swp2.entity.question.MultipleChoice;
import be.ehb.swp2.entity.question.TrueFalseQuestion;
import be.ehb.swp2.ui.*;
import org.hibernate.SessionFactory;

import java.util.ArrayList;


public class QuizLauncher implements Window {
    private SessionFactory factory;
    private ArrayList<QuestionWindow> windows;
    private ArrayList<String> antwoorden;
    private int increment = 0;
    private boolean running = true;

    public QuizLauncher(SessionFactory factory) {
        this.factory = factory;
        windows = new ArrayList<QuestionWindow>();
        antwoorden = new ArrayList<String>();
        System.out.println("test1");


        antwoorden.add("kraai");
        antwoorden.add("twitter");
        antwoorden.add("duif");
        MultipleChoice mc = new MultipleChoice("title", "wat is this?", AnswerType.MULTIPLE_CHOICE, AnswerMediaType.EMPTY, antwoorden.get(1));
        TrueFalseQuestion tc = new TrueFalseQuestion("title", "wat is that?", AnswerType.TRUE_FALSE, AnswerMediaType.EMPTY, 0, true);

        windows.clear();
        windows.add(new TextWindow(factory, mc, this));
        windows.add(new ImageWindow(factory, "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png", tc, this));
        windows.add(new VideoWindow(factory, "u1I9ITfzqFs", mc, this));
        windows.add(new AudioWindow(factory, "u1I9ITfzqFs", tc, this));

        this.windowChoice();
    }

    public void initComponents() {


    }

    public void windowChoice() {

        System.out.println("test test");
        if (windows.get(increment) instanceof TextWindow) {
            TextWindow t = (TextWindow) windows.get(increment);
            t.initComponents();
            System.out.println("textwindow");
        }

        if (windows.get(increment) instanceof ImageWindow) {
            ImageWindow p = (ImageWindow) windows.get(increment);
            p.initComponents();
            System.out.println("Imagewindow");
        }

        if (windows.get(increment) instanceof VideoWindow) {
            VideoWindow v = (VideoWindow) windows.get(increment);
            v.initComponents();
            System.out.println("Videowindow");
        }

        if (windows.get(increment) instanceof AudioWindow) {
            AudioWindow a = (AudioWindow) windows.get(increment);
            a.initComponents();
            System.out.println("Audiowindow");
        }
    }

    public int getIncrement() {
        return increment;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getWindowsSize() {
        return windows.size();
    }
}

