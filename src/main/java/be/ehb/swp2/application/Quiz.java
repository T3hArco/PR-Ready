package be.ehb.swp2.application;

import be.ehb.swp2.entity.AnswerType;
import be.ehb.swp2.entity.Question;
import be.ehb.swp2.exception.DuplicateQuestionException;
import be.ehb.swp2.exception.QuizNotFoundException;
import be.ehb.swp2.manager.QuestionAnswerManager;
import be.ehb.swp2.manager.QuestionManager;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.ui.LoadingWindow;
import be.ehb.swp2.ui.LoginWindow;
import be.ehb.swp2.ui.OverviewWindow;
import be.ehb.swp2.util.ConfigurationHandler;
import be.ehb.swp2.util.LoadingThread;
import be.ehb.swp2.util.PieChartData;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
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
    private ConfigurationHandler configurationHandler;// !
    Thread loadingWindow = new Thread(new LoadingThread());

    /**
     * Default constructor.
     */
    public Quiz() {
        logger = Logger.getLogger(Quiz.class.getName());
        //loadingWindow.start();
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

        logger.info("Disabling obnoxious logging");
        LoggerProvider.getChromiumProcessLogger().setLevel(Level.OFF);
        LoggerProvider.getIPCLogger().setLevel(Level.OFF);
        LoggerProvider.getBrowserLogger().setLevel(Level.OFF);

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
        configurationHandler = new ConfigurationHandler();

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
        LoginWindow lw = new LoginWindow(factory);
        loadingWindow.stop();
    }

    /**
     * Testing method, inserts three rows in database
     *
     * @deprecated To be deprecated and never used in production!
     */
    public void doDbTest() {
       /* QuestionAnswerManager qa = new QuestionAnswerManager(factory);
        qa.addQuestionAnswer(1, 1, true);
        qa.addQuestionAnswer(1, 2, false);
        qa.addQuestionAnswer(1, 3, false);*/
        /*QuestionManager qm = new QuestionManager(factory);
        try {
            qm.addQuestion("question6","????", AnswerType.MULTIPLE_CHOICE, null,1);
        } catch (DuplicateQuestionException e) {
            e.printStackTrace();
        }*/
    }

    public void imageSaveTest() {
        QuizManager quizManager = new QuizManager(factory);
        SecureRandom random = new SecureRandom();
        Integer quizId = null;
        be.ehb.swp2.entity.Quiz quiz = null;

        try {
            quizId = quizManager.addQuiz("pliep", "test");
            quiz = quizManager.getQuizById(quizId);
        } catch (QuizNotFoundException e) {
            logger.log(Level.SEVERE, "\"Fout tijdens het aanmaken van de quiz\"");
            e.printStackTrace();
        }

        try {
            URL url = new URL("file:///Users/arnaudcoel/Desktop/icons/water.png");
            quizManager.saveLogo(quizId, url);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "\"Fout tijdens het aanmaken van de afbeelding\"");
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Quiz q = new Quiz();
        q.doDbTest();
    }
}