package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;

/**
 * Created by Thomas on 29/10/2015.
 */
public class MathQuestion extends Question {

    private int answer;

    public MathQuestion(int answer, String name, String description, int time, boolean timeOn){
        super(name,description, time, timeOn);
        this.answer = answer;
    }

    public void setAnswer(int answer){
        this.answer = answer;
    }

    public int getAnswer(){
        return this.answer;
    }

    public boolean solve(int userAnswer){
        if(userAnswer == this.answer) {
            return true;
        } else {
        return false;}
    }
}
