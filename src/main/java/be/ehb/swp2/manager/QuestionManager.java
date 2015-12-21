package be.ehb.swp2.manager;

import be.ehb.swp2.entity.AnswerMediaType;
import be.ehb.swp2.entity.AnswerType;
import be.ehb.swp2.entity.Question;
import be.ehb.swp2.exception.DuplicateQuestionException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by arnaudcoel on 26/11/15.
 */

public class QuestionManager {
    private SessionFactory factory;

    public QuestionManager(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * This method will add a question to the database
     *
     * @param title
     * @param text
     * @param questionType
     * @param questionExtraId
     * @return
     * @throws DuplicateQuestionException
     */
    public Integer addQuestion(String title, String text, AnswerType questionType, AnswerMediaType answerMediaType, Integer questionExtraId) throws DuplicateQuestionException {
        Session session = factory.openSession();
        Transaction transaction = null;
        Integer questionId = null;

        try {
            transaction = session.beginTransaction(); // start een transactie op
            Question question = new Question(title, text, questionType, answerMediaType, 1);
            questionId = (Integer) session.save(question); // geef de ID van de gebruiker weer
            transaction.commit(); // persist in de database
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback(); // maak de transactie ongedaan indien er een fout is
        } finally {
            session.close(); // we zijn klaar en sluiten onze sessie af
        }

        if (questionId == null)
            throw new DuplicateQuestionException();

        return questionId; // geeft de aangemaakte userAnswer weer
    }

}
