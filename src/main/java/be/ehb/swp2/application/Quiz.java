package be.ehb.swp2.application;

import javax.swing.*;

import be.ehb.swp2.manager.ConfigManager;
import be.ehb.swp2.manager.LoginManager;
import be.ehb.swp2.manager.QuizManager;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.ui.LoginWindow;
import com.mysql.jdbc.log.Log4JLogger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ConfigManager configManager;

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
        configManager = new ConfigManager();
        configManager.setSession("TEST");
        configManager.getSession();

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

        LoginWindow lw = new LoginWindow(factory);



    }

    /**
     * Testing method, inserts three rows in database
     * @deprecated To be deprecated and never used in production!
     */
    public void doDbTest() {


        UserManager um = new UserManager(factory);

        /*QuizManager qm = new QuizManager(factory);
        Integer q1 = qm.addQuiz("Test", "Test", "Dit is een test quiz!");*/

        //System.out.println(qm.getQuizById(q1).getName());


        um.listEmployeesToConsole();

        LoginManager lm = new LoginManager(factory);
        System.out.println(lm.isValidLogin("a", "a"));
    }
}
