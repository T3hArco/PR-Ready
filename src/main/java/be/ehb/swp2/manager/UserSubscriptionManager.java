package be.ehb.swp2.manager;

import be.ehb.swp2.entity.UserSubscription;
import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.exception.UserNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by arnaudcoel on 26/11/15.
 */
public class UserSubscriptionManager {
    private SessionFactory factory;

    public UserSubscriptionManager(SessionFactory factory) {
        this.factory = factory;
    }

    public void register(Integer userId, Integer quizId) throws QuizNotFoundException, UserNotFoundException {
        Session session = factory.openSession();
        Transaction transaction = null;
        QuizManager qm = null;
        UserManager um = null;

        try {
            transaction = session.beginTransaction();
            qm = new QuizManager(factory);
            um = new UserManager(factory);

            qm.exists(quizId);
            um.exists(userId);

            UserSubscription userSubscription = new UserSubscription(quizId, userId);
            session.save(userSubscription);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public boolean isRegistered(Integer userId, Integer quizId, boolean register) throws QuizNotFoundException, UserNotFoundException {
        Session session = factory.openSession();
        Transaction transaction = null;
        QuizManager qm = null;
        UserManager um = null;
        UserSubscription subscription = null;

        try {
            transaction = session.beginTransaction();
            qm = new QuizManager(factory);
            um = new UserManager(factory);

            qm.exists(quizId);
            um.exists(userId);

            UserSubscription other = new UserSubscription(quizId, userId);
            subscription = (UserSubscription) session.get(UserSubscription.class, other);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }

        if(subscription == null) {
            if(register)
                this.register(userId, quizId);

            return false;
        }

        return true;
    }
}
