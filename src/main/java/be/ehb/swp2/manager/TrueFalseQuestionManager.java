package be.ehb.swp2.manager;

import be.ehb.swp2.entity.question.QuestionType;
import be.ehb.swp2.entity.question.TrueFalseQuestion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Thomas on 12/11/2015.
 */
public class TrueFalseQuestionManager {
    private SessionFactory factory;


    /**
     * constructor voor TrueFalseQuestionManager
     * @param factory
     */

    public TrueFalseQuestionManager(SessionFactory factory){this.factory = factory;}

    /** deze functie zal een nieuwe true or false question aanmaken
     *
     */
    /** wtfjkjk */
    public Integer addQuestion(String name, String description, int time, boolean timeOn, QuestionType questionType, boolean answer){
        Session session = factory.openSession();
        Transaction transaction = null;
        Integer questionId = null;


        try {
            transaction = session.beginTransaction();
            TrueFalseQuestion trueFalseQuestion = new TrueFalseQuestion(name, description, time, timeOn, questionType, answer);
            questionId = (Integer) session.save(trueFalseQuestion);
            transaction.commit(); // persist in de database
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback(); // maak de transactie ongedaan indien er een fout is
        } finally {
            session.close(); // we zijn klaar en sluiten onze sessie af
        }

        return questionId; // geef de aangemaakte Id van de true or false question
    }





}




