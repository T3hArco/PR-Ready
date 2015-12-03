package be.ehb.swp2.ui.swing;

import be.ehb.swp2.entity.User;
import be.ehb.swp2.exception.BadLoginException;
import be.ehb.swp2.exception.DuplicateUserException;
import be.ehb.swp2.exception.InternalErrorException;
import be.ehb.swp2.manager.LoginManager;
import be.ehb.swp2.manager.UserManager;
import be.ehb.swp2.ui.Window;
import be.ehb.swp2.util.Configurator;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by arnaudcoel on 30/11/15.
 */
public class SwingAdminWindow extends JFrame implements Window {
    private SessionFactory factory;
    private Configurator configurator;
    private UserManager userManager;

    public SwingAdminWindow(SessionFactory factory) {
        this.factory = factory;
        this.configurator = new Configurator();
        this.userManager = new UserManager(factory);

        this.initComponents();
    }

    public void initComponents() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JList userList = null;
        final JButton createButton = new JButton("Create");
        final JButton deleteButton = new JButton("Delete");

        deleteButton.setEnabled(false);

        userList = this.getList();

        final JList finalUserList = userList;
        userList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if(finalUserList.getSelectedIndex() == -1)
                    deleteButton.setEnabled(false);
                else
                    deleteButton.setEnabled(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JList finalUserList1 = finalUserList;
                User user = ((User) finalUserList.getSelectedValue());
                userManager.setUserState(user.getId(), false);
                JOptionPane.showMessageDialog(null, user.getUsername() + " is uitgeschakeld.", "PR-Ready", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JPanel parent = new JPanel(new GridLayout(2, 1));
        this.add(parent);

        parent.add(userList);

        JPanel options = new JPanel(new GridLayout(1, 2));
        options.add(createButton);
        options.add(deleteButton);
        parent.add(options);

        this.pack();
        this.setVisible(true);
    }

    public JList getList() {
        JList list = null;

        try {
            list = new JList(userManager.listUsers());
        } catch (InternalErrorException e) {
            JOptionPane.showMessageDialog(null, "Gebruikerslijst kon niet opgehaald worden.", "PR-Ready", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return list;
    }
}