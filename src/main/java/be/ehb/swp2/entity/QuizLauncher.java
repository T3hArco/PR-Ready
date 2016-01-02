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
        windows.add(new TextWindow(factory, mc));
        windows.add(new ImageWindow(factory, "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png", tc));
        windows.add(new VideoWindow(factory, "u1I9ITfzqFs", mc));
        windows.add(new AudioWindow(factory, "u1I9ITfzqFs", tc));



        this.initComponents();
    }

    public void initComponents() {

        int increment = 0;
        boolean running = true;

//2255



        while (running != false) {
            System.out.println("test test");
            if (windows.get(increment).getClass().getSimpleName().equals("TextWindow")) {
                TextWindow t = (TextWindow) windows.get(increment);
                System.out.println(increment);
                if (t.getChoice() == 1) {

                    increment++;


                }
                else if (t.getChoice() == 2) {


                    increment--;


                }
            }

           else if (windows.get(increment).getClass().getSimpleName().equals("ImageWindow")) {
                ImageWindow p = (ImageWindow) windows.get(increment);
                System.out.println(increment);
                if (p.getChoice() == 1) {

                    increment++;


                }
                else if (p.getChoice() == 2) {


                    increment--;


                }

            }

           else if (windows.get(increment).getClass().getSimpleName().equals("VideoWindow")) {
                VideoWindow v = (VideoWindow) windows.get(increment);
                System.out.println(increment);
                if (v.getChoice() == 1) {

                    increment++;


                }
                else if (v.getChoice() == 2) {


                    increment--;


                }
            }

            else if (windows.get(increment).getClass().getSimpleName().equals("AudioWindow")) {
                AudioWindow a = (AudioWindow) windows.get(increment);
                System.out.println(increment);
                if (a.getChoice() == 1) {

                    increment++;


                }
                else if (a.getChoice() == 2) {


                    increment--;


                }
            }

           else if (increment >= windows.size())
                running = false;
        }
    }
}

