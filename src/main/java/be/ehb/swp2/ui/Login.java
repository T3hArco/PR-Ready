package be.ehb.swp2.ui;

/**
 * Created by domienhennion on 4/11/15.
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;

    /**
     * Launch the application.
     */
    /*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
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
    public Login() {
        setForeground(SystemColor.desktop);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        SpringLayout sl_contentPane = new SpringLayout();
        contentPane.setLayout(sl_contentPane);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.BLUE));
        sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 89, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, panel, 64, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, 178, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, panel, 376, SpringLayout.WEST, contentPane);
        contentPane.add(panel);
        panel.setLayout(new GridLayout(0, 2));

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setForeground(Color.LIGHT_GRAY);
        panel.add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblPassword);

        textFieldPassword = new JTextField();
        textFieldPassword.setForeground(Color.LIGHT_GRAY);
        panel.add(textFieldPassword);
        textFieldPassword.setColumns(10);

        JLabel lblLogin = new JLabel("LOGIN");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblLogin, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblLogin, 164, SpringLayout.WEST, contentPane);
        lblLogin.setFont(new Font("Meiryo", Font.PLAIN, 30));
        lblLogin.setForeground(Color.BLUE);
        lblLogin.setBackground(Color.YELLOW);
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblLogin);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar cal = Calendar.getInstance();
        JLabel lblDate = new JLabel(dateFormat.format(cal.getTime()));
        sl_contentPane.putConstraint(SpringLayout.EAST, lblLogin, -77, SpringLayout.WEST, lblDate);
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblDate, 0, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, lblDate, 0, SpringLayout.EAST, contentPane);
        lblDate.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        contentPane.add(lblDate);

        JButton btnLogin = new JButton("Login");
        sl_contentPane.putConstraint(SpringLayout.WEST, btnLogin, 153, SpringLayout.WEST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.SOUTH, btnLogin, -27, SpringLayout.SOUTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnLogin, 11, SpringLayout.EAST, lblLogin);
        contentPane.add(btnLogin);
    }
}
