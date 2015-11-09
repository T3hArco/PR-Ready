package be.ehb.swp2.ui;

/**
 * Created by Christophe on 3/11/2015.
 */
        import javax.swing.JPanel;
        import javax.swing.JTextArea;
        import javax.swing.JTextField;
        import javax.swing.SpringLayout;

        import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.FlowLayout;
        import java.awt.TextField;

        import javax.swing.DropMode;
        import javax.swing.JComboBox;

public class Question extends JPanel {

    SpringLayout layout;

    public Question(int parrentWidth,Color questionColor) {
        //create new panel for questions with red background and spring layout
        layout = new SpringLayout();
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(parrentWidth,100));
        this.setBackground(questionColor);



        //add text area
//		JTextArea txtrTextAreaTest = new JTextArea();
//		txtrTextAreaTest.setPreferredSize(new Dimension(parrentWidth/2,50));
//		txtrTextAreaTest.setLineWrap(true);
//		txtrTextAreaTest.setRows(5);
//		txtrTextAreaTest.setToolTipText("New Question");
//		layout.putConstraint(SpringLayout.VERTICAL_CENTER, txtrTextAreaTest,0,SpringLayout.VERTICAL_CENTER, this);
//		this.add(txtrTextAreaTest);
//		
        JTextArea field = new JTextArea();
        field.setUI(new HintTextAreaUI("New Question", true));
        field.setPreferredSize(new Dimension(parrentWidth/2,50));
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, field,0,SpringLayout.VERTICAL_CENTER, this);
        this.add(field);

        //add comboBox
        JComboBox comboBox = new JComboBox();
        comboBox.setPreferredSize(new Dimension(parrentWidth/3,50));
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, comboBox,0,SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, comboBox,25,SpringLayout.EAST, field);
        this.add(comboBox);

        //precalculate layout before actual drawing (to get dimensions - bounds etc)
        this.doLayout();

        //debug bounds
        System.out.println("txtrTextAreaTest question bounds = " + field.getBounds());
        System.out.println("comboBox question bounds = " +	comboBox.getBounds());
    }

}
