package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;

/**
 * Created by Ibrahim on 03-11-15.
 */

/**
 * Klasse voor een T/F question
 */
public class TrueFalseQuestion extends Question {
    /**
     * Het uiteindelijke antwoord, true of false.
     */
    private boolean answer;

    /**
     * De constructor voor een true/false svraag
     * @param name
     * @param description
     * @param time
     * @param timeOn
     * @param answer
     */
    public TrueFalseQuestion(String name, String description, int time, boolean timeOn, boolean answer) {
        super(name, description, time, timeOn);
        this.answer = answer;
    }

    /**
     * Zet het juiste antwoord
     * @param answer
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    /**
     * Geeft het juiste antwoord weer
     * @return
     */
    public boolean getAnswer() {
        return this.answer;
    }

    /**
     * Retourneert of wat er ingegeven is, juist is.
     * @param answer
     * @return
     */
    public boolean solve(boolean answer){
        if(answer == this.answer)
            return true;

        return false;
    }

}
