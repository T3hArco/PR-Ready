package be.ehb.swp2.entity;

import be.ehb.swp2.entity.question.MultipleChoice;
import be.ehb.swp2.ui.*;

import java.util.ArrayList;



public class QuizLauncher {

    static ArrayList<questionWindow> windows = new ArrayList<questionWindow>();
    static ArrayList<Question> questions = new ArrayList<Question>();

    String title = "Alpha";
    String text = "Alpha release quiz";

    public QuizLauncher() {
        ArrayList<String> antwoorden = new ArrayList<String>();
        antwoorden.add("kraai");
        antwoorden.add("twitter");
        antwoorden.add("duif");
        MultipleChoice mc = new MultipleChoice("title", "text", QuestionType.MULTIPLE_CHOICE, 0, antwoorden.get(1));
        questions.add(mc);
        windows.add(new TextWindow("Wat is ..... ?"));
        windows.add(new ImageWindow("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png", "Wat zijn dit?"));
        windows.add(new VideoWindow("ovU1xUT4vRk"));
        windows.add(new AudioWindow("u1I9ITfzqFs"));


    }



   public void launch() {
        QuizLauncher q = new QuizLauncher();
        int i = 0;
        boolean b = true;
        while (b != false) {


            if (windows.get(i).getClass().getSimpleName().equals("TextWindow")){
                TextWindow t = (TextWindow) windows.get(i);
                t.printGui();
            }
            if (windows.get(i).getClass().getSimpleName().equals("ImageWindow")){
                ImageWindow p = (ImageWindow) windows.get(i);
                p.printGui();
            }
             if (windows.get(i).getClass().getSimpleName().equals("VideoWindow")){
                VideoWindow v = (VideoWindow) windows.get(i);
                v.printGui();
            }
            if (windows.get(i).getClass().getSimpleName().equals("AudioWindow")){
                AudioWindow a = (AudioWindow) windows.get(i);
                a.printGui();
            }
            if (windows.get(i).getClass().getSimpleName().equals(null)) {
                b = false;
            }
            i++;
        }



    }
}

