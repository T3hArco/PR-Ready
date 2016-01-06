package be.ehb.swp2.manager;

import be.ehb.swp2.entity.Answer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.*;
//import org.hibernate.sql.ordering.antlr.Factory;

/**
 * Created by Ibrahim on 10-12-15.
 */
public class AnswerManager {

    public SessionFactory factory;

    public AnswerManager(SessionFactory factory) {
        this.factory = factory;
    }

    public Integer addAnswer(int answerId, String text) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer id = null;

        try {
            transaction = session.beginTransaction();
            Answer answer = new Answer(answerId, text);
            id = (Integer) session.save(answer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return id;
    }


    public List<String> getAnswerByQuestionId(Integer questionId){
        Session session = factory.openSession();
        Transaction transaction = null;
        List answers = null;

        try {
            transaction = session.beginTransaction();
            Query fetchQuestions = session.createQuery("From Answer join QuestionAnswer on Answer.answerId == QuestionAnswer.answerId where QuestionAnswer.questionId == " + questionId + "");
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

    public List<String> getAnswersByQuestionId(Integer questionId) {
        String url;
        Session session = factory.openSession();

        Query query = session.createSQLQuery("select text From Answer a, QuestionAnswer q where a.answerId = q.answerId and q.questionId = :questionId");
        query.setParameter("questionId", questionId);
        List<String> answers = (List<String>) query.list();






        // Check whether the list is empty, if so, no users are matched, thus return false
        //String url = (String) linklist.get(0)[0];
        //String url = linklist.get(0);
        /*System.out.println("HOERA");
        session.close();*/

        return answers;
    }


}
