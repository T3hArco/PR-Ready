package be.ehb.swp2.application;

import javax.swing.*;

import be.ehb.swp2.entity.Question;
import be.ehb.swp2.entity.QuestionType;
import be.ehb.swp2.entity.User;
import be.ehb.swp2.entity.UserSubscription;
import be.ehb.swp2.exception.DuplicateAnswerException;
import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.exception.UserNotFoundException;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.manager.UserAnswerManager;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.manager.UserSubscriptionManager;
import be.ehb.swp2.ui.LoginWindow;
import be.ehb.swp2.ui.OverviewWindow;
import be.ehb.swp2.ui.test.SwingTestMain;
import be.ehb.swp2.util.Configurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by arnaudcoel on 23/10/15.
 */

/**
 * Class that handles the initialization of our Quiz
 */
public class Quiz {
    private SessionFactory factory;
    private Logger logger;
    private Configurator configurator;// !
    /**
     * Default constructor.
     */
    public Quiz() {
        logger = Logger.getLogger(Quiz.class.getName());

        this.initialize();
    }

    /**
     * Provides initialization of project
     */
    private void initialize() {
        long start = System.currentTimeMillis();
        Logger.getLogger("org.hibernate").setLevel(Level.ALL);

        System.out.println("    ____  ____        ____  _________    ______  __\n" +
                "   / __ \\/ __ \\      / __ \\/ ____/   |  / __ \\ \\/ /\n" +
                "  / /_/ / /_/ /_____/ /_/ / __/ / /| | / / / /\\  / \n" +
                " / ____/ _, _/_____/ _, _/ /___/ ___ |/ /_/ / / /  \n" +
                "/_/   /_/ |_|     /_/ |_/_____/_/  |_/_____/ /_/   ");
        System.out.println("------------------------------------------------");

        logger.info("Starting application");
        logger.info("Setting JFrame Look and Feel");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        logger.info("Starting configuration manager");
        configurator = new Configurator();

        logger.info("Starting database");
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch(Throwable e) {
            System.err.println("Failed to instantiate database: " + e);
            throw new ExceptionInInitializerError(e);
        }

        long end = System.currentTimeMillis();
        long totalTime = end - start;
        logger.info("Initialization took " + totalTime + " milliseconds!");

        /*SwingLoginWindow lw = new SwingLoginWindow(factory);
        SwingTestMain mw = new SwingTestMain(factory);*/

        /*SwingTestMain mw = new SwingTestMain(factory); // deprecated, but for testing purposes.
        LoginWindow lw = new LoginWindow(factory);*/

        UserManager um = new UserManager(factory);
        try {
            um.addUser("test", "test");
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }

        UserAnswerManager uam = new UserAnswerManager(factory);
        try {
            uam.addUserAnswer(1, 1, "Dit is een hele leuke test!");
            uam.addUserAnswer(1, 1, "Dit is een hele leuke test! Kan dit tweemaal?");
            uam.addUserAnswer(1, 2, "Dit is een hele leuke test!");
        } catch (DuplicateAnswerException e) {
            e.printStackTrace();
        }

        QuizManager qm = new QuizManager(factory);
        qm.addQuiz("Testquiz", "http://google.com", "Dit is een hele leuke test quiz");

        try {
            int q = qm.getQuizById(1).getId();
            qm.addQuestionToQuiz(q, new Question("Pliep", "Pleos", QuestionType.OPEN, 1));
        } catch (QuizNotFoundException e) {
            e.printStackTrace();
        }

        UserSubscriptionManager us = new UserSubscriptionManager(factory);
        try {
            us.isRegistered(1, 1, true);
            us.isRegistered(1, 1, false);

            us.register(1, 1);
            // todo implement check is registered, kthx
        } catch (QuizNotFoundException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        SwingTestMain mw = new SwingTestMain(factory); // deprecated, but for testing purposes.
        LoginWindow lw = new LoginWindow(factory);
        OverviewWindow ow = new OverviewWindow(factory);
        ow.printGui();
    }

    /**
     * Testing method, inserts three rows in database
     * @deprecated To be deprecated and never used in production!
     */
    public void doDbTest() {

    }
}
