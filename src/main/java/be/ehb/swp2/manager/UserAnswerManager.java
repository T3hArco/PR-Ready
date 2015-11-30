package be.ehb.swp2.manager;

import be.ehb.swp2.entity.UserAnswer;
import be.ehb.swp2.exception.DuplicateAnswerException;
import be.ehb.swp2.exception.DuplicateUserException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by arnaudcoel on 26/11/15.
 */
public class UserAnswerManager {
    private SessionFactory factory;

    public UserAnswerManager(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Will add an answer for a user
     * @param userId
     * @param questionId
     * @param answer
     * @return
     * @throws DuplicateAnswerException
     */
    public UserAnswer addUserAnswer(Integer userId, Integer questionId, String answer) throws DuplicateAnswerException {
        Session session = factory.openSession();
        Transaction transaction = null;
        UserAnswer finalObject = null;

        try {
            transaction = session.beginTransaction(); // start een transactie op
            UserAnswer userAnswer = new UserAnswer(userId, questionId, answer);
            finalObject = (UserAnswer) session.save(userAnswer); // geef de ID van de gebruiker weer
            transaction.commit(); // persist in de database
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback(); // maak de transactie ongedaan indien er een fout is
        } finally {
            session.close(); // we zijn klaar en sluiten onze sessie af
        }

        if(userId == null)
            throw new DuplicateAnswerException();

        return finalObject; // geeft de aangemaakte userAnswer weer
    }

    public void updateUserQuestion(Integer userId, Integer questionId, String answer) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            UserAnswer search = new UserAnswer(userId, questionId, answer);
            UserAnswer userAnswer = (UserAnswer) session.get(UserAnswer.class, search); // haal de user op die we proberen te referencen
            userAnswer.setAnswer(answer); // zet de nieuwe naam van de gebruiker
            session.update(userAnswer); // zet de update klaar
            transaction.commit(); // TaDa
        } catch (HibernateException e) {
            // TODO implementeer manier om doubles er uit te filteren
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
