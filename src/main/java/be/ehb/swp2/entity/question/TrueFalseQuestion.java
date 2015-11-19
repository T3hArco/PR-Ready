package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.manager.TrueFalseQuestionManager;
import org.hibernate.SessionFactory;
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
    QuestionType questionType;
    private static SessionFactory factory;
    /**
     * De constructor voor een true/false svraag
     * @param name
     * @param description
     * @param time
     * @param timeOn
     * @param questionType
     * @param answer
     */
    public TrueFalseQuestion(String name, String description, int time, boolean timeOn, QuestionType questionType, boolean answer) {
        super(name, description, time, timeOn);
        this.answer = answer;
        this.questionType = QuestionType.trueOrFalse;
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

    public QuestionType getQuestionType() {
        return questionType;
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

    /**
     *
     * de addQuestion methode uittesten, kijken of hij iets toevoegd aan de database.
     * @param args
     */
    public static void main(String[] args){
        TrueFalseQuestionManager T1 = new TrueFalseQuestionManager(factory);
        QuestionType questionType = null;
        T1.addQuestion("Q1","test", 10, false, questionType, false);
    }

}
