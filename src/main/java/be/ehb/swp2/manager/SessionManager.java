package be.ehb.swp2.manager;

/**
 * Created by arnaudcoel on 29/10/15.
 */

import be.ehb.swp2.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * De Hibernate manager voor sessies
 */
@Deprecated
public class SessionManager {
    private SessionFactory factory;

    /**
     * Constructor voor SessionManager
     *
     * @param factory
     */
    public SessionManager(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Gaat op basis van een gegeven token de geldigheid er van controleren.
     *
     * @param token
     * @return true/false
     */
    public boolean tokenExists(String token) {
        Session session = factory.openSession();
        Transaction transaction = null;
        boolean isValid = false;

        try {
            transaction = session.beginTransaction();

            List<Object[]> tokenList = session.createQuery("SELECT userid, token, expires FROM Session WHERE token = :token")
                    .setMaxResults(1)
                    .setParameter("token", token)
                    .list();

            return tokenList.size() != 0;

        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return isValid;
    }

    public String createToken(User user) {
        /*if(user == null)
            return null;

        Session session = factory.openSession();
        Transaction transaction = null;
        LoginManager lm = new LoginManager(factory);
        Integer sessionId = null;
        String token = null;

        try {
            token = user.generateToken();
            transaction = session.beginTransaction();

            Query query = session.createSQLQuery("INSERT INTO Sessions (userid, token, expires) VALUES(:userid, :token, :expires);")
                    .setParameter("userid", user.getId())
                    .setParameter("token", token)
                    .setParameter("expires", null);

            query.executeUpdate();

            transaction.commit();
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return token;*/

        return null;
    }
}
