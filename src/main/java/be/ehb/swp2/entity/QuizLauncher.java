package be.ehb.swp2.entity;

import be.ehb.swp2.entity.question.MultipleChoice;
import be.ehb.swp2.ui.questionWindow;

import java.util.ArrayList;

/**
 * Created by domienhennion on 9/12/15.
 */
public class QuizLauncher {
    ArrayList<questionWindow> windows = new ArrayList<questionWindow>();
    ArrayList<Question> questions = new ArrayList<Question>();

    String title = "Alpha";
    String text = "Alpha release quiz";

    public QuizLauncher(){
        ArrayList<String> antwoorden = new ArrayList<String>();
        antwoorden.add("kraai");
        antwoorden.add("twitter");
        antwoorden.add("duif");
        antwoorden.add("duif");
        MultipleChoice mc = new MultipleChoice(title, text, 1, antwoorden, antwoorden.get(1));
        questions.add(mc);
    }
}
