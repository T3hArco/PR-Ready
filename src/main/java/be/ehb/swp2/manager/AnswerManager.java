package be.ehb.swp2.manager;

import be.ehb.swp2.entity.Answer;
import org.hibernate.*;

import java.util.List;
//import org.hibernate.sql.ordering.antlr.Factory;

/**
 * Created by Ibrahim on 10-12-15.
 */
public class AnswerManager {
    private SessionFactory factory;

    public AnswerManager(SessionFactory factory) {
        this.factory = factory;
    }

    public Integer addAnswer(Answer answer) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer id = null;

        Answer newAnswer = new Answer();
        newAnswer.setText(answer.getText());

        if(newAnswer.getAnswerId() == 0)
            newAnswer.setAnswerId(1);

        try {
            transaction = session.beginTransaction();
            System.out.println("newAnswer = " + newAnswer.toString());
            id = (Integer) session.save(newAnswer);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
                e.printStackTrace();
        } finally {
            session.close();
        }
        return id;
    }


    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        Session session = factory.openSession();
        Transaction transaction = null;
        List answers = null;

        try {
            transaction = session.beginTransaction();
            Query fetchQuestions = session.createQuery("From Answer where id = :questionId")
                    .setInteger("questionId", questionId);

            answers = fetchQuestions.list();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }

        return answers;
    }



    public void deleteAnswer(Integer id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Answer answer = session.get(Answer.class, id);
            session.delete(answer);
            transaction.commit(); //
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void updateAnswer(int id, int answerId) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Answer answer = session.get(Answer.class, id);
            answer.setAnswerId(answerId);
            session.update(answer);
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
