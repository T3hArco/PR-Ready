package be.ehb.swp2.entity;

import be.ehb.swp2.entity.question.MultipleChoice;
import be.ehb.swp2.entity.question.TrueFalseQuestion;
import be.ehb.swp2.ui.*;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;

import java.util.ArrayList;



public class QuizLauncher {

    static ArrayList<questionWindow> windows = new ArrayList<questionWindow>();


    String title = "Alpha";
    String text = "Alpha release quiz";

    public QuizLauncher() {
        ArrayList<String> antwoorden = new ArrayList<String>();
        antwoorden.add("kraai");
        antwoorden.add("twitter");
        antwoorden.add("duif");
        MultipleChoice mc = new MultipleChoice("title", "wat is this?", AnswerType.MULTIPLE_CHOICE, antwoorden.get(1));
        TrueFalseQuestion tc = new TrueFalseQuestion("title", "wat is that?", AnswerType.TRUE_FALSE, 0, true);

        windows.clear();
        windows.add(new TextWindow(mc));
        windows.add(new ImageWindow("https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png", tc));
        windows.add(new VideoWindow("u1I9ITfzqFs", mc));
        windows.add(new AudioWindow("u1I9ITfzqFs", tc));


    }



   public void launch() {
        QuizLauncher q = new QuizLauncher();
        int i = 0;
        boolean b = true;
        while (b != false) {


            if (windows.get(i).getClass().getSimpleName().equals("TextWindow")){
                TextWindow t = (TextWindow) windows.get(i);
                t.printGui();
                if (t.getChoice() == 1) {
                    i++;
                }
                else if (t.getChoice() == 2){
                    i--;
                }
            }
            if (windows.get(i).getClass().getSimpleName().equals("ImageWindow")){
                ImageWindow p = (ImageWindow) windows.get(i);
                p.printGui();
                if (p.getChoice() == 1) {
                    i++;
                }
                else if (p.getChoice() == 2){
                    i--;
                }
            }
             if (windows.get(i).getClass().getSimpleName().equals("VideoWindow")){
                VideoWindow v = (VideoWindow) windows.get(i);
                v.printGui();
                 if (v.getChoice() == 1) {
                     i++;
                 }
                 else if (v.getChoice() == 2){
                     i--;
                 }
            }
            if (windows.get(i).getClass().getSimpleName().equals("AudioWindow")){
                AudioWindow a = (AudioWindow) windows.get(i);
                a.printGui();
                if (a.getChoice() == 1) {
                    i++;
                }
                else if (a.getChoice() == 2){
                    i--;
                }
            }
            if (windows.get(i).getClass().getSimpleName().equals(null)) {
                b = false;
            }

        }



    }
}

