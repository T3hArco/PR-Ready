package be.ehb.swp2.manager;
import be.ehb.swp2.entity.question.QuestionAnswer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by Ibrahim on 10-12-15.
 */
public class QuestionAnswerManager {

    public SessionFactory factory;
    public QuestionAnswerManager(SessionFactory factory) {this.factory = factory;}

    public  Integer addQuestionAnswer( boolean correct) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer id = null;

        try {
            transaction = session.beginTransaction();
            QuestionAnswer questionanswer = new QuestionAnswer(correct);
            id = (Integer) session.save(questionanswer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return id;
    }

    public void deleteQuestionAnswer(Integer id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            QuestionAnswer questionanswer = session.get(QuestionAnswer.class, id);
            session.delete(questionanswer);
            transaction.commit(); //
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void updateQuestionAnswer( int questionId, int answerId, boolean correct) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            QuestionAnswer questionanswer = session.get(QuestionAnswer.class,questionId);
            questionanswer.setQuestionId(questionId);
            questionanswer.setAnswerId(answerId);
            questionanswer.setCorrect(correct);
            session.update(questionanswer);
            transaction.commit();
        } catch (HibernateException e) {

            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
