package be.ehb.swp2.manager;

import be.ehb.swp2.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import java.util.Iterator;
import java.util.List;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class UserManager {
    private SessionFactory factory;

    public UserManager(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Deze functie zal trachten een gebruiker toe te voegen aan de databank. Na afloop van de functie wordt er een
     * foutmelding weergegeven of een gebruikersId (Integer)
     * @param name De naam van de gebruiker
     * @param password Het ongeencrypteerde wachtwoord van de gebruiker, dat hier ook geencrypteerd zal worden.
     * @return userId
     */
    public Integer addUser(String name, String password) {
        Session session = factory.openSession();
        Transaction transaction = null;
        Integer userId = null;

        try {
            transaction = session.beginTransaction(); // start een transactie op
            User user = new User(name, password); // TODO implement encryption
            userId = (Integer) session.save(user); // geef de ID van de gebruiker weer
            transaction.commit(); // persist in de database
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback(); // maak de transactie ongedaan indien er een fout is
        } finally {
            session.close(); // we zijn klaar en sluiten onze sessie af
        }

        return userId; // geef de aangemaakte Id van de gebruiker
    }

    /**
     * Deze method zal alle employees in de console afdrukken. Het is de bedoeling dat deze op zijn beurt wordt omgezet
     * naar een functie die gewoon een List terug geeft in plaats van een void.
     */
    public void listEmployeesToConsole() {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List users = session.createQuery("FROM User").list();

            for(Iterator iterator = users.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                System.out.println("User " + user.getId() + ": " + user.getName());
            }

            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Deze method zal de username van een gebruiker updaten doormiddel van de userId
     * @param userId
     * @param name
     */
    public void updateUserName(Integer userId, String name) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, userId); // haal de user op die we proberen te referencen
            user.setName(name); // zet de nieuwe naam van de gebruiker
            session.update(name); // zet de update klaar
            transaction.commit(); // TaDa
        } catch (HibernateException e) {
            // TODO implementeer manier om doubles er uit te filteren
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public User getUserById(Integer userId) {
        Session session = factory.openSession();
        Transaction transaction = null;
        User user = null;

        try {
            transaction = session.beginTransaction();
            user = (User) session.get(User.class, userId); // haal de user op via ID
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

    /**
     * Deze methode zal doormiddel van de gebruikersiD het wachtwoord van de gebruiker veranderen.
     * @param userId
     * @param password
     */
    public void updatePassword(Integer userId, String password) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, userId); // haal de user op die we proberen te referencen
            user.setPassword(password); // zet de nieuwe naam van de gebruiker
            session.update(password); // zet de update klaar
            transaction.commit(); // TaDa
        } catch (HibernateException e) {
            // TODO implementeer manier om doubles er uit te filteren
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * Deze methode zal doormiddel van de userId de gebruiker uit de database verwijderen.
     * @param userId
     */
    public void deleteUser(Integer userId) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, userId);
            session.delete(user);
            transaction.commit(); // weg er mee!
        } catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
