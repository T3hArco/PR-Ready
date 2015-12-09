package be.ehb.swp2.manager;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.Quiz;
import be.ehb.swp2.exception.QuizNotFoundException;
import org.hibernate.*;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by arnaudcoel on 26/10/15.
 */
public class QuizManager {
	private static final Logger LOGGER = Logger.getLogger(QuizManager.class);
	private SessionFactory factory;

	/**
	 * Constructor voor QuizManager
	 *
	 * @param factory
	 */
	public QuizManager(SessionFactory factory) {
		this.factory = factory;
	}

    /**
     * Deze functie gaat een quiz invoegen in de databank
	 *
	 * @param name
     * @param description
     * @return
     */
	public Integer addQuiz(String name, String description) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Integer quizId = null;

		try {
			transaction = session.beginTransaction(); // start een transactie op
			Quiz quiz = new Quiz(name, description);
			quizId = (Integer) session.save(quiz); // geef de ID van de quiz
			// weer
			transaction.commit(); // persist in de database
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback(); // maak de transactie ongedaan indien er
			// een fout is
		} finally {
			session.close(); // we zijn klaar en sluiten onze sessie af
		}

		return quizId; // geef de aangemaakte Id van de gebruiker
	}

    /**
     * Method will attempt to save an image to the database added to a quiz.
	 *
	 * @param quizId
     * @param blob
     * @throws IOException
     */
    private void saveLogo(Integer quizId, Blob blob) throws IOException {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
			Quiz quiz = session.get(Quiz.class, quizId);
			byte[] logoBytes = blob.getBytes(1, (int) blob.length());
            quiz.setLogo(logoBytes);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    /**
	 * Deze method zal de naam van een quiz updaten doormiddel van de quizId
	 *
	 * @param quizId
	 * @param name
	 */
	public void updateUserName(Integer quizId, String name) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Quiz quiz = session.get(Quiz.class, quizId); // haal de quiz
			// op die we
			// proberen te
			// referencen
			quiz.setTitle(name); // zet de nieuwe naam van de gebruiker
			session.update(name); // zet de update klaar
			transaction.commit(); // TaDa
		} catch (HibernateException e) {
			// TODO implementeer manier om doubles er uit te filteren
			if (transaction != null)
				transaction.rollback();

			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public Quiz getQuizById(Integer quizId) throws QuizNotFoundException {
		Session session = factory.openSession();
		Transaction transaction = null;
		Quiz quiz = null;

		try {
			transaction = session.beginTransaction();
			quiz = session.get(Quiz.class, quizId); // haal de quiz op
			// via ID
			// transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();

			e.printStackTrace();
		} finally {
			session.close();
		}

		if (quiz == null)
			throw new QuizNotFoundException();

		return quiz;
	}

	/**
	 * Gets all quizzes in the database
	 *
	 * @return quizzes
	 */
	public List<Quiz> getQuizzes() {
		Session session = factory.openSession();
		Transaction transaction = null;
		List quizzes = null;

		try {
			transaction = session.beginTransaction();
			Query fetchQuizzes = session.createQuery("From Quiz");
			quizzes = fetchQuizzes.list();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();

			e.printStackTrace();
		} finally {
			session.close();
		}

		return quizzes;
	}

	/**
	 * @param quiz
	 * @return
	 * @todo implement
	 */
	public Question getQuestionsForQuiz(Integer quiz) {
		return null;
	}

	/**
	 * Deze methode zal doormiddel van de quizId de quiz uit de database
	 * verwijderen.
	 *
	 * @param quizId
	 */
	public void deleteUser(Integer quizId) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Quiz quiz = session.get(Quiz.class, quizId);
			session.delete(quiz);
			transaction.commit(); // weg er mee!
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();

			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void addQuestionToQuiz(Integer quizId, Question question) {
		Session session = factory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Quiz quiz = session.get(Quiz.class, quizId);
			quiz.addQuestion(question);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();

			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public boolean exists(Integer quizId) throws QuizNotFoundException {
		return this.getQuizById(quizId) != null;

	}
}
