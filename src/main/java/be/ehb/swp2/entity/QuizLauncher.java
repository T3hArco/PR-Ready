package be.ehb.swp2.entity;

import be.ehb.swp2.entity.question.MultipleChoice;
import be.ehb.swp2.entity.question.TrueFalseQuestion;
import be.ehb.swp2.ui.ImageWindow;
import be.ehb.swp2.ui.TextWindow;
import be.ehb.swp2.ui.VideoWindow;
import be.ehb.swp2.ui.questionWindow;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;

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
        windows.add(new TextWindow(mc.getText()));
        windows.add(new ImageWindow(mc.getText(), "img"));
        windows.add(new VideoWindow(mc.getText()));


    }



   static  public void main(String[] args) {
        QuizLauncher q = new QuizLauncher();
        int i = 0;
       boolean b = true;
        while (b != false){

            if (windows.get(i).getClass().getSimpleName().equals("TextWindow")){
                TextWindow t = (TextWindow) windows.get(i);
                t.main(args);
            }
            if (windows.get(i).getClass().getSimpleName().equals("ImageWindow")){
                ImageWindow p = (ImageWindow) windows.get(i);
                p.main(args);
            }
             if (windows.get(i).getClass().getSimpleName().equals("VideoWindow")){
                VideoWindow v = (VideoWindow) windows.get(i);
                v.main(args);
            }
            if (windows.get(i).getClass().getSimpleName().equals(null)) {
                b = false;
            }
            i++;
        }



    }
}

