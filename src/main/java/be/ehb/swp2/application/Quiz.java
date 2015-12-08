package be.ehb.swp2.application;

import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.ui.LoginWindow;
import be.ehb.swp2.ui.OverviewWindow;
import be.ehb.swp2.util.Configurator;
import be.ehb.swp2.util.ImageHandler;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
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
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

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
        } catch (Throwable e) {
            System.err.println("Failed to instantiate database: " + e);
            throw new ExceptionInInitializerError(e);
        }

        long end = System.currentTimeMillis();
        long totalTime = end - start;
        logger.info("Initialization took " + totalTime + " milliseconds!");

        imageSaveTest();

        LoginWindow lw = new LoginWindow(factory);
        OverviewWindow ow = new OverviewWindow(factory);
        ow.printGui();
    }

    /**
     * Testing method, inserts three rows in database
     *
     * @deprecated To be deprecated and never used in production!
     */
    public void doDbTest() {

    }

    public void imageSaveTest() {
        QuizManager quizManager = new QuizManager(factory);
        SecureRandom random = new SecureRandom();
        Integer quizId = null;
        be.ehb.swp2.entity.Quiz quiz = null;

        try {
            quizId = quizManager.addQuiz(new BigInteger(130, random).toString(32), "test");
            quiz = quizManager.getQuizById(quizId);
        } catch (QuizNotFoundException e) {
            logger.log(Level.SEVERE, "\"Fout tijdens het aanmaken van de quiz\"");
            e.printStackTrace();
        }

        try {
            quiz.setLogo(ImageHandler.toByteArray(Quiz.class.getResource("/images/unknown.png")));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "\"Fout tijdens het aanmaken van de afbeelding\"");
            e.printStackTrace();
        }
    }
}