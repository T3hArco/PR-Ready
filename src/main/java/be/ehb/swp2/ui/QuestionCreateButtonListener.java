package be.ehb.swp2.ui;

/**
 * Created by Christophe on 3/11/2015.
 */
        import java.awt.Color;
        import java.awt.Dimension;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

        import javax.swing.JButton;
        import javax.swing.JPanel;
        import javax.swing.SpringLayout;

public class QuestionCreateButtonListener implements ActionListener {


    private Editor parentPanel;
    private JPanel panelInsideScrollPane;
    private JButton buttonTest;
    private int colorCounter;

    public QuestionCreateButtonListener(Editor parentPanel) {
        this.parentPanel = parentPanel;
        this.panelInsideScrollPane = parentPanel.getPanelInsideScrollPane();
        this.buttonTest = parentPanel.getButtonTest();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("klik");

        //change color
        colorCounter ++;
        colorCounter = (colorCounter < parentPanel.getColors().length) ? colorCounter : 0;
        parentPanel.setCurrentColor(parentPanel.getColors()[colorCounter]);

        //Create Question panel
        Question newElement = new Question((int)panelInsideScrollPane.getPreferredSize().getWidth(), parentPanel.getCurrentColor());
        parentPanel.getLayout().putConstraint(SpringLayout.NORTH, newElement,parentPanel.getVerticalMargin(),SpringLayout.SOUTH, parentPanel.CalculateNextOffsetFromThisComponent);
        parentPanel.CalculateNextOffsetFromThisComponent = newElement;






        //Add Question Panel to the inner pannel (pannel within the scrollpane)
        panelInsideScrollPane.add(newElement);
        panelInsideScrollPane.validate();

        //Current preferred panel size
        int x = (int) panelInsideScrollPane.getPreferredSize().getWidth();
        int y = (int) panelInsideScrollPane.getPreferredSize().getHeight();


        //If next elements is outside preferred bounds --> grow pannel
        if((newElement.getLocation().y + (newElement.getHeight() )) >= (buttonTest.getLocation().y - 60))
        {
            panelInsideScrollPane.setPreferredSize(new Dimension(x,y + newElement.getHeight() + parentPanel.getVerticalMargin()));
        }


        parentPanel.getFrame().pack();
        parentPanel.getVerticalScrollBar().setValue( parentPanel.getVerticalScrollBar().getMaximum() );


    }

}
