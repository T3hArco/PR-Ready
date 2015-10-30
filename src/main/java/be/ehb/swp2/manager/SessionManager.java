package be.ehb.swp2.manager;

/**
 * Created by arnaudcoel on 29/10/15.
 */

import be.ehb.swp2.entity.User;
import org.hibernate.*;

import java.util.List;

/**
 * De Hibernate manager voor sessies
 */
public class SessionManager {
    private SessionFactory factory;

    /**
     * Constructor voor SessionManager
     * @param factory
     */
    public SessionManager(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Gaat op basis van een gegeven token de geldigheid er van controleren.
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

            if(tokenList.size() == 0)
                return false; // no tokens were found, so it must be fake

            return true;
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return isValid;
    }

    public String createToken(User user) {
        if(user == null)
            return null;

        Session session = factory.openSession();
        Transaction transaction = null;
        LoginManager lm = new LoginManager(factory);
        Integer sessionId = null;
        String token = null;

        try {
            token = user.generateToken();
            transaction = session.beginTransaction();
            be.ehb.swp2.entity.Session userSession = new be.ehb.swp2.entity.Session(user, token, null);
            sessionId = (Integer) session.save(userSession);
            transaction.commit();
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }

        return token;
    }
}
