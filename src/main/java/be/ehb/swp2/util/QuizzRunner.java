package be.ehb.swp2.util;

/**
 * Created by Ibrahim on 02-12-15.
 */

import be.ehb.swp2.application.Quiz;
import be.ehb.swp2.manager.QuizManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;


import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Created by Ibrahim on 27-11-15.
 */


public class QuizzRunner {
    private static final Logger LOGGER = Logger.getLogger(Quiz.class.getName());
    private Configurator configurator;
    private SessionFactory factory;
    private QuizManager qm;

    public static void main(String[] args) {
        QuizzRunner qm = new QuizzRunner();
        qm.initialize();
        qm.showQuizzForm();
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private void showQuizzForm() {
        // Create and set up the window.
        JFrame frame = new JFrame("Quizz form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the window.
        frame.add(new QuizzPanel(frame,qm));

        // Display the window.
        frame.pack();
        frame.setSize(600,600);
        frame.setVisible(true);
    }

    /**
     * Provides initialization of project
     */
    private void initialize() {
        long start = System.currentTimeMillis();
        Logger.getLogger("org.hibernate").setLevel(Level.ALL);

        System.out.println("    ____  ____        ____  _________    ______  __\n"
                + "   / __ \\/ __ \\      / __ \\/ ____/   |  / __ \\ \\/ /\n"
                + "  / /_/ / /_/ /_____/ /_/ / __/ / /| | / / / /\\  / \n"
                + " / ____/ _, _/_____/ _, _/ /___/ ___ |/ /_/ / / /  \n"
                + "/_/   /_/ |_|     /_/ |_/_____/_/  |_/_____/ /_/   ");
        System.out.println("------------------------------------------------");

        LOGGER.info("Starting application");
        LOGGER.info("Setting JFrame Look and Feel");

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

        LOGGER.info("Starting configuration manager");
        configurator = new Configurator();

        LOGGER.info("Starting database");
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Failed to instantiate database: " + e);
            throw new ExceptionInInitializerError(e);
        }

        long end = System.currentTimeMillis();
        long totalTime = end - start;
        LOGGER.info("Initialization took " + totalTime + " milliseconds!");

        qm = new QuizManager(factory);

    }
}