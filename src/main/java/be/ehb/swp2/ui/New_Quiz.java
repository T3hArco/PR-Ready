package be.ehb.swp2.ui;

/**
 * Created by domienhennion on 4/11/15.
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;

public class New_Quiz extends JFrame {

    private JPanel contentPane;
    private JTextField textField;

    /**
     * Launch the application.
     */
    /*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    New_Quiz frame = new New_Quiz();
                    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
*/
    /**
     * Create the frame.
     */
    public New_Quiz() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewQuiz = new JLabel("New Quiz");
        lblNewQuiz.setBounds(174, 10, 92, 25);
        lblNewQuiz.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewQuiz.setForeground(Color.BLUE);
        panel.add(lblNewQuiz);

        JLabel lblQuizName = new JLabel("Quiz Name");
        lblQuizName.setBounds(61, 90, 69, 16);
        panel.add(lblQuizName);

        JLabel lblQuizLogo = new JLabel("Quiz Logo");
        lblQuizLogo.setBounds(61, 138, 64, 16);
        panel.add(lblQuizLogo);

        JButton btnBrowse = new JButton("Browse...");
        btnBrowse.setBounds(155, 134, 130, 29);
        btnBrowse.setFont(new Font("Gurmukhi MN", Font.PLAIN, 13));
        panel.add(btnBrowse);

        textField = new JTextField();
        textField.setBounds(155, 85, 130, 26);
        panel.add(textField);
        textField.setColumns(10);

        JButton btnCreate = new JButton("Create");
        btnCreate.setBounds(178, 195, 84, 29);
        panel.add(btnCreate);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(355, 10, 75, 29);
        btnBack.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 13));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.BLUE);
        panel.add(btnBack);
    }

}

