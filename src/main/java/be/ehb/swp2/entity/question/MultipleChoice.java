package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Thomas on 29/10/2015.
 */
public class MultipleChoice extends Question {
    private Vector<String>options;
    private int answer;

    public MultipleChoice(Vector<String> options, int answer, String name, String description, int time, boolean timeOn){
        super(name,description, time, timeOn);
        this.answer = answer;
        for (int i=0; i<options.size(); i++) {
            this.options.add(options.elementAt(i));
        }
    }

    public void setAnswer(int answer){
        this.answer = answer;
    }

    public int getAnswer(){
        return this.answer;
    }

    public boolean addOption(String newOption){
        if(this.options.add(newOption)){
            return true;
        }
        return false;
    }

    public boolean removeOption(int optionNo){
        String temp = options.elementAt(optionNo);
        if(temp == this.options.remove(optionNo)){
            return true;
        }
        return false;
    }

    public boolean solve(int userAnswer) {
        if(userAnswer == this.answer){
            return true;
        }
        return false;
    }
}
