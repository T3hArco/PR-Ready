package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.AnswerMediaType;
import be.ehb.swp2.entity.AnswerType;
import be.ehb.swp2.entity.Question;
import org.hibernate.SessionFactory;

/**
 * Created by Ibrahim on 03-11-15.
 */

/**
 * Klasse voor een T/F question
 */
public class TrueFalseQuestion extends Question {
    private static SessionFactory factory;
    AnswerType answerType;
    /**
     * Het uiteindelijke antwoord, true of false.
     */
    private boolean answer;

    public TrueFalseQuestion(String title, String text, AnswerType answerType, AnswerMediaType answerMediaType, int questionExtraId, boolean answer) {
        super(title, text, answerType, answerMediaType, 1, 1);
        this.answer = answer;
        this.answerType = AnswerType.TRUE_FALSE;
    }

    /**
     * Geeft het juiste antwoord weer
     *
     * @return
     */
    public boolean getAnswer() {
        return this.answer;
    }

    /**
     * Zet het juiste antwoord
     *
     * @param answer
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public AnswerType getAnswerType() {
        return answerType;
    }


    /**
     * Retourneert of wat er ingegeven is, juist is.
     *
     * @param answer
     * @return
     */
    public boolean solve(boolean answer) {
        return answer == this.answer;

    }

}
