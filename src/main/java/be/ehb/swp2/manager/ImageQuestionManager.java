package be.ehb.swp2.manager;


import be.ehb.swp2.entity.question.ImageQuestion;
import org.hibernate.*;

/**
 * Created by Ibrahim on 10-12-15.
 */
public class ImageQuestionManager {

    public SessionFactory factory;

    public ImageQuestionManager(SessionFactory factory) {
        this.factory = factory;
    }
    // public ImageQuestionManager(){};

    public Integer addImageQuestion(int parentQuestion, String link) {

        Session session = factory.openSession();
        Transaction transaction = null;
        Integer id = null;

        try {
            transaction = session.beginTransaction();
            ImageQuestion imagequestion = new ImageQuestion(parentQuestion, link);
            id = (Integer) session.save(imagequestion);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return id;
    }

    public void deleteImageQuestion(Integer id) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            ImageQuestion imagequestion = session.get(ImageQuestion.class, id);
            session.delete(imagequestion);
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
        Query query = session.createQuery("SELECT link from ImageQuestion where parentId = :parentId");
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


    public void updateImageQuestion(int id, String link, int parentQuestion) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            ImageQuestion imagequestion = session.get(ImageQuestion.class, id);
            imagequestion.setLink(link);
            imagequestion.setParentId(parentQuestion);
            session.update(imagequestion);
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
