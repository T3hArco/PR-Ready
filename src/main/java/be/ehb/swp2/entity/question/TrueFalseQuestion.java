package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuestionType;
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

    public TrueFalseQuestion(String title, String text, QuestionType questionType, Integer questionExtraId, boolean answer) {
        super(title, text, questionType, questionExtraId);
        this.answer = answer;
        this.questionType = QuestionType.TRUE_FALSE;
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

}
