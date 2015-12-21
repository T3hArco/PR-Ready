package be.ehb.swp2.ui;

/**
 * Created by Christophe on 3/11/2015.
 */

import be.ehb.swp2.ui.test.HintTextAreaUI;

import javax.swing.*;
import java.awt.*;

public class Question extends JPanel implements Window {
    private SpringLayout layout;
    private int parentWidth;
    private Color questionColor;

    public Question(int parentWidth, Color questionColor) {
        layout = new SpringLayout();
        this.parentWidth = parentWidth;
        this.questionColor = questionColor;
    }

    public void initComponents() {
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(parentWidth, 100));
        this.setBackground(questionColor);

        JTextArea field = new JTextArea();
        field.setUI(new HintTextAreaUI("New Question", true));
        field.setPreferredSize(new Dimension(parentWidth / 2, 50));
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, field, 0, SpringLayout.VERTICAL_CENTER, this);
        this.add(field);

        //add comboBox
        JComboBox comboBox = new JComboBox();
        comboBox.setPreferredSize(new Dimension(parentWidth / 3, 50));
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, comboBox, 0, SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, comboBox, 25, SpringLayout.EAST, field);
        this.add(comboBox);

        //precalculate layout before actual drawing (to get dimensions - bounds etc)
        this.doLayout();

        //debug bounds
        System.out.println("txtrTextAreaTest question bounds = " + field.getBounds());
        System.out.println("comboBox question bounds = " + comboBox.getBounds());
    }
}
