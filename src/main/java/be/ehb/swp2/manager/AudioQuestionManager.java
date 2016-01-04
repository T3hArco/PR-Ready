package be.ehb.swp2.manager;

import be.ehb.swp2.entity.question.AudioQuestion;
import org.hibernate.*;

import java.util.List;

//import be.ehb.swp2.entity.Quiz;

/**
 * Created by Ibrahim on 10-12-15.
 */
public class AudioQuestionManager {
    public SessionFactory factory;

    public AudioQuestionManager(SessionFactory factory) {
        this.factory = factory;
    }

    public Integer addAudioQuestion(int parentQuestion, String link) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Integer id = null;

        try {
            transaction = session.beginTransaction();
            AudioQuestion audioquestion = new AudioQuestion(link, parentQuestion);
            id = (Integer) session.save(audioquestion);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return id;
    }

    public void deleteAudioQuestion(Integer id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            AudioQuestion audioquestion = session.get(AudioQuestion.class, id);
            session.delete(audioquestion);
            transaction.commit(); //
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void updateAudioQuestion(int id, String link, int parentQuestion) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            AudioQuestion audioquestion = session.get(AudioQuestion.class, id);
            audioquestion.setLink(link);
            audioquestion.setParentId(parentQuestion);
            session.update(audioquestion);
            transaction.commit();
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
        Query query = session.createQuery("SELECT link from AudioQuestion where parentId = :parentId");
                query.setParameter("parentId", questionId);
        url = (String) query.uniqueResult();

        System.out.println("HOERA");

        // Check whether the list is empty, if so, no users are matched, thus return false
        //String url = (String) linklist.get(0)[0];
        //String url = linklist.get(0);
        /*System.out.println("HOERA");
        session.close();*/
        System.out.println(url);
        return url;
    }


    public AudioQuestion getAudioById(Integer QuestionId) {
        Session session = factory.openSession();
        Transaction transaction = null;
        AudioQuestion aq = null;
        Integer parentId = QuestionId;
        try {
            transaction = session.beginTransaction();
            aq = session.get(AudioQuestion.class, parentId);

        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }

        return aq;
    }

/*
    AudioQuestionManager aqm = new AudioQuestionManager(factory);

    public static void main (String [] args)
    {

        AudioQuestionManager aqm = new AudioQuestionManager();
        aqm.addAudioQuestion(3,"TEST");

    }


*/
}
