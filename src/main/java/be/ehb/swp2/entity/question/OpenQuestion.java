package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;

/**
 * Created by Thomas on 29/10/2015.
 */
public class OpenQuestion extends Question {
    String answer;


    public boolean solve(String answer){
        return true;
    }

    @Override
    public boolean solve() {
        return false;
    }
}