package be.ehb.swp2.entity;

import be.ehb.swp2.manager.AudioQuestionManager;
import be.ehb.swp2.manager.ImageQuestionManager;
import be.ehb.swp2.manager.QuestionManager;
import be.ehb.swp2.manager.VideoQuestionManager;
import be.ehb.swp2.ui.*;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;


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
        QuestionManager qm = new QuestionManager(factory);

        List<Question> questions = null;
        try {
            questions = qm.getQuestionsByQuizId(6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Question q : questions){
            System.out.println(q.getTitle());
            System.out.println(q.getAnswerMediaType());
        }

        for (Question q : questions){

                if (q.getAnswerMediaType().equals(AnswerMediaType.EMPTY)) {

                    windows.add(new TextWindow(factory, q, this));

                }
                if (q.getAnswerMediaType().equals(AnswerMediaType.AUDIO)){

                    AudioQuestionManager aqm = new AudioQuestionManager(factory);
                    String url = aqm.getUrlById(2);
                    windows.add(new AudioWindow(factory, url , q, this));

                }
                if(q.getAnswerMediaType().equals(AnswerMediaType.IMAGE)){
                    ImageQuestionManager iqm = new ImageQuestionManager(factory);
                    String url = iqm.getUrlById(2);
                    windows.add(new ImageWindow(factory, url, q, this));
                }
                if (q.getAnswerMediaType().equals(AnswerMediaType.VIDEO)){
                    VideoQuestionManager vqm = new VideoQuestionManager(factory);
                    String url = vqm.getUrlById(2);
                    windows.add(new VideoWindow(factory, url, q, this));
                }
        }




        for (QuestionWindow q : windows){
            System.out.println("lol");
            System.out.println(q.getClass().getSimpleName());

        }

       /* windows.add(new TextWindow(factory, mc, this));
        windows.add(new ImageWindow(factory, "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png", tc, this));
        windows.add(new VideoWindow(factory, "u1I9ITfzqFs", mc, this));
        windows.add(new AudioWindow(factory, "u1I9ITfzqFs", tc, this));*/



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

