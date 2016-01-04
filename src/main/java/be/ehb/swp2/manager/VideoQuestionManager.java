package be.ehb.swp2.manager;

import be.ehb.swp2.entity.question.VideoQuestion;
import org.hibernate.*;

/**
 * Created by Ibrahim on 10-12-15.
 */
public class VideoQuestionManager {
    public SessionFactory factory;

    public VideoQuestionManager(SessionFactory factory) {
        this.factory = factory;
    }
    // public VideoQuestionManager(){};

    public Integer addVideoQuestion(int parentQuestion, String link) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer id = null;

        try {
            transaction = session.beginTransaction();
            VideoQuestion videoquestion = new VideoQuestion(parentQuestion, link);
            id = (Integer) session.save(videoquestion);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return id;
    }

    public void deleteVideoQuestion(Integer id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            VideoQuestion videoquestion = session.get(VideoQuestion.class, id);
            session.delete(videoquestion);
            transaction.commit(); //
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public String getUrlById(Integer questionId) {
        String url;
        Session session = factory.openSession();
        System.out.println("HOERA");
        Query query = session.createQuery("SELECT link from VideoQuestion where parentId = :parentId");
        query.setParameter("parentId", questionId);
        url = (String) query.uniqueResult();

        System.out.println("HOERA");

        // Check whether the list is empty, if so, no users are matched, thus return false
        //String url = (String) linklist.get(0)[0];
        //String url = linklist.get(0);
        /*System.out.println("HOERA");
        session.close();*/
        System.out.println("HOERA");
        return url;
    }

    public void updateVideoQuestion(int id, String link, int parentQuestion) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            VideoQuestion videoquestion = session.get(VideoQuestion.class, id);
            videoquestion.setLink(link);
            videoquestion.setParentId(parentQuestion);
            session.update(videoquestion);
            transaction.commit();
        } catch (HibernateException e) {

            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }


   /* public static void main (String [] args)
    {


    }*/
}
