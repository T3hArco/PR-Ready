package be.ehb.swp2.entity.question;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuestionType;
import org.hibernate.SessionFactory;

/**
 * Created by Ibrahim on 03-11-15.
 */

/**
 * Klasse voor een T/F question
 */
public class TrueFalseQuestion extends Question {
    private static SessionFactory factory;
    QuestionType questionType;
    /**
     * Het uiteindelijke antwoord, true of false.
     */
    private boolean answer;

    public TrueFalseQuestion(String title, String text, QuestionType questionType, int questionExtraId, boolean answer) {
        super(title, text, null);
        this.answer = answer;
        this.questionType = QuestionType.TRUE_FALSE;
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

    public QuestionType getQuestionType() {
        return questionType;
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
