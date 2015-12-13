package be.ehb.swp2.manager;
import be.ehb.swp2.entity.question.AudioQuestion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by Ibrahim on 10-12-15.
 */
public class AudioQuestionManager {
    public SessionFactory factory;

    public AudioQuestionManager(SessionFactory factory) {this.factory = factory;}
   // public AudioQuestionManager(){};

    public Integer addAudioQuestion( int parentQuestion, String link) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer id = null;

        try {
            transaction = session.beginTransaction();
            AudioQuestion audioquestion = new AudioQuestion(parentQuestion, link);
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


    public void updateAudioQuestion( int id, String link, int parentQuestion) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            AudioQuestion audioquestion = session.get(AudioQuestion.class,id);
            audioquestion.setLink(link);
            audioquestion.setParentQuestion(parentQuestion);
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


   /* public static void main (String [] args)
    {


    }*/
}
