package be.ehb.swp2.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.Quiz;
import be.ehb.swp2.exception.QuizNotFoundException;

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
	 * Deze functie zal trachten een quiz toe te voegen aan de databank. Na
	 * afloop van de functie wordt er een foutmelding weergegeven of een quizId
	 * (Integer)
	 * 
	 * @param name
	 *            De naam van de quiz
	 * @param logo
	 *            Een URL naar het logo van de quiz
	 * @param description
	 *            Een beschrijving van de quiz
	 * @return quizId
	 */
	public Integer addQuiz(String name, String logo, String description) {
		Session session = factory.openSession();
		Transaction transaction = null;
		Integer quizId = null;

		try {
			transaction = session.beginTransaction(); // start een transactie op
			Quiz quiz = new Quiz(name, logo, description);
			quiz.setLogoImg(getImageFromPath(logo, session));
			quizId = (Integer) session.save(quiz); // geef de ID van de quiz
													// weer
			transaction.commit(); // persist in de database
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback(); // maak de transactie ongedaan indien er
										// een fout is
		} catch (FileNotFoundException e) {
			LOGGER.error("The file could not be found", e);
		} finally {
			session.close(); // we zijn klaar en sluiten onze sessie af
		}

		return quizId; // geef de aangemaakte Id van de gebruiker
	}

	/***
	 * create a blob from file path
	 * 
	 * @param filePath
	 *            file path
	 * @param session
	 *            hibernate session
	 * @return blob
	 * @throws FileNotFoundException
	 *             file not found
	 */

	// private Blob --> Ibrahim

	private Blob getImageFromPath(String filePath, Session session) throws FileNotFoundException {
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		Blob blob = Hibernate.getLobCreator(session).createBlob(inputStream, file.length());
		return blob;
	}
// Blob methode door Ibrahim voor Db
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
			Quiz quiz = (Quiz) session.get(Quiz.class, quizId); // haal de quiz
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
			quiz = (Quiz) session.get(Quiz.class, quizId); // haal de quiz op
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
	 * @todo implement
	 * @param quiz
	 * @return
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
			Quiz quiz = (Quiz) session.get(Quiz.class, quizId);
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
			Quiz quiz = (Quiz) session.get(Quiz.class, quizId);
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
		if (this.getQuizById(quizId) == null)
			return false;

		return true;
	}
}
