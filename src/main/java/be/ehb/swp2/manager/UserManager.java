package be.ehb.swp2.manager;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.TokenNotFoundException;
import be.ehb.swp2.exception.UserNotFoundException;
import org.hibernate.*;

import java.util.Iterator;
import java.util.List;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class UserManager {
    private SessionFactory factory;

    /**
     * Constructor voor UserManager
     * @param factory
     */
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
    public Integer addUser(String name, String password) throws DuplicateUserException {
        Session session = factory.openSession();
        Transaction transaction = null;
        Integer userId = null;

        try {
            transaction = session.beginTransaction(); // start een transactie op
            User user = new User(name, password);
            userId = (Integer) session.save(user); // geef de ID van de gebruiker weer
            transaction.commit(); // persist in de database
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback(); // maak de transactie ongedaan indien er een fout is
        } finally {
            session.close(); // we zijn klaar en sluiten onze sessie af
        }

        if(userId == null)
            throw new DuplicateUserException();

        return userId; // geef de aangemaakte Id van de gebruiker
    }

    /**
     * Deze method zal alle employees in de console afdrukken. Het is de bedoeling dat deze op zijn beurt wordt omgezet
     * naar een functie die gewoon een List terug geeft in plaats van een void.
     */
    public void listUsersToConsole() {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            List users = session.createQuery("FROM User").list();

            for(Iterator iterator = users.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                System.out.println("User " + user.getId() + ": " + user.getUsername());
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
            user.setUsername(name); // zet de nieuwe naam van de gebruiker
            session.update(user); // zet de update klaar
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
     * Gaat automatisch een unieke token genereren voor de gegeven gebruiker
     * @param userId
     * @return unieke token
     */
    public String setToken(Integer userId) {
        Session session = factory.openSession();
        Transaction transaction = null;
        String token = null;

        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, userId);
            token = user.setToken(); // laat de token genereren
            session.update(user);
            transaction.commit();
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }

        return token;
    }

    /**
     * Collects the token for the user
     * @param userId
     * @return the user's token
     * @throws TokenNotFoundException
     */
    public String getToken(Integer userId) throws TokenNotFoundException {
        Session session = factory.openSession();
        Transaction transaction = null;
        String token = null;

        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, userId);
            token = user.getToken();
        } catch(HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        }

        if(token == null)
            throw new TokenNotFoundException();

        return token;
    }

    /**
     * This is a very icky function, Hibernate does not allow us to find an user with their respective token, so we must
     * do out own socery.
     * @param token
     * @return User object that uses the token or an exception
     * @throws TokenNotFoundException if the user has not been located via token
     */
    public User getUserByToken(String token) throws TokenNotFoundException, UserNotFoundException {
        Session session = factory.openSession();

        List<Object[]> userList = session.createQuery("SELECT id, username, password FROM User WHERE (token = :token)")
                .setMaxResults(1)
                .setParameter("token", token)
                .list();

        // Check whether the list is empty, if so, no users are matched, thus return false
        if(userList.size() == 0)
            throw new TokenNotFoundException();

        int userId = Integer.parseInt(userList.get(0)[0].toString());

        session.close();

        User user = new UserManager(factory).getUserById(userId);
        return user;
    }

    /**
     * Deze methode gaat de gebruiker doormiddel van zijn ID ophalen
     * @param userId
     * @return
     */
    public User getUserById(Integer userId) throws UserNotFoundException {
        Session session = factory.openSession();
        Transaction transaction = null;
        User user = null;

        try {
            transaction = session.beginTransaction();
            user = (User) session.get(User.class, userId); // haal de user op via ID
        } catch (HibernateException e) {
            if(transaction != null)
                transaction.rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }

        if(user == null)
            throw new UserNotFoundException();

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
            session.update(user); // zet de update klaar
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
