package be.ehb.swp2.ui.test;

/**
 * Created by Christophe on 3/11/2015.
 */

import javax.swing.*;
import java.awt.*;


public class Editor {

    //Datamembers
    //-------------------------

    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    //used to store last created component;
    public JComponent CalculateNextOffsetFromThisComponent;
    //Window
    JFrame frame;
    //Panels
    //Scroll panel
    JScrollPane myScrollPane;
    JScrollBar verticalScrollBar;
    int verticalMargin = 5;
    //Inner content panel
    JPanel panelInsideScrollPane;
    //Layout for panelInsideScrollPane
    SpringLayout layout;
    //Components
    JButton buttonTest;
    //QuestionCreateButtonListener myListener;
    //colors
    Color[] colors = new Color[3];
    Color currentColor;


    //----------------------------------------------------

    //Constructor
    public Editor() {

        //init color array
        colors[0] = new Color(0x90C3D4);
        colors[1] = new Color(0x67AFC7);
        colors[2] = new Color(0x75A1C7);
        currentColor = colors[0];


        //Create window
        createWindow();

        //Create panels
        createPanels();

        //Add first question panels
        //addUIQuestion();

        //Set size for the panels
        panelInsideScrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        myScrollPane.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        //Add button
        buttonTest = new JButton("add component");
        //myListener = new QuestionCreateButtonListener(this);
        //buttonTest.addActionListener(myListener);
        panelInsideScrollPane.add(buttonTest);

        //Place button bottom right
        layout.putConstraint(SpringLayout.SOUTH, buttonTest, -25, SpringLayout.SOUTH, panelInsideScrollPane);
        layout.putConstraint(SpringLayout.EAST, buttonTest, -25, SpringLayout.EAST, panelInsideScrollPane);

        //Show window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    //Entry point
    public static void main(String args[]) {
        System.out.println("Editor start");
        Editor test = new Editor();
    }

    //getters setters
    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JScrollPane getMyScrollPane() {
        return myScrollPane;
    }

    public void setMyScrollPane(JScrollPane myScrollPane) {
        this.myScrollPane = myScrollPane;
    }

    public JScrollBar getVerticalScrollBar() {
        return verticalScrollBar;
    }

    public void setVerticalScrollBar(JScrollBar verticalScrollBar) {
        this.verticalScrollBar = verticalScrollBar;
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public void setVerticalMargin(int verticalMargin) {
        this.verticalMargin = verticalMargin;
    }

    public JPanel getPanelInsideScrollPane() {
        return panelInsideScrollPane;
    }

    public void setPanelInsideScrollPane(JPanel panelInsideScrollPane) {
        this.panelInsideScrollPane = panelInsideScrollPane;
    }

    public SpringLayout getLayout() {
        return layout;
    }

    public void setLayout(SpringLayout layout) {
        this.layout = layout;
    }

    public JButton getButtonTest() {
        return buttonTest;
    }

    public void setButtonTest(JButton buttonTest) {
        this.buttonTest = buttonTest;
    }

    /*public QuestionCreateButtonListener getMyListener() {
        return myListener;
    }*/

    /*public void setMyListener(QuestionCreateButtonListener myListener) {
        this.myListener = myListener;
    }*/

    public JComponent getCalculateNextOffsetFromThisComponent() {
        return CalculateNextOffsetFromThisComponent;
    }

    public void setCalculateNextOffsetFromThisComponent(
            JComponent calculateNextOffsetFromThisComponent) {
        CalculateNextOffsetFromThisComponent = calculateNextOffsetFromThisComponent;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    //----------------------------------------------------

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void createWindow() {

        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public void createPanels() {
        //Create scrollpane
        myScrollPane = new JScrollPane();

        //Create  panel with spring layout(for inside the scrollpane)
        panelInsideScrollPane = new JPanel();
        layout = new SpringLayout();
        panelInsideScrollPane.setLayout(layout);

        //Add panel to the scrollpane
        myScrollPane.setViewportView(panelInsideScrollPane);
        verticalScrollBar = new JScrollBar();
        myScrollPane.setVerticalScrollBar(verticalScrollBar);


        myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        myScrollPane.getVerticalScrollBar().setUnitIncrement(32);

        //Add scrollpane to window
        frame.add(myScrollPane);


    }

    /*public void addUIQuestion() {
        //Create a first question panel

        Question newElement = new Question(WINDOW_WIDTH, currentColor);
        layout.putConstraint(SpringLayout.NORTH, newElement, 5, SpringLayout.NORTH, panelInsideScrollPane);
        System.out.println("new element bounds =" + newElement.getBounds());


        panelInsideScrollPane.add(newElement);
        panelInsideScrollPane.doLayout();


        System.out.println("new element bounds =" + newElement.getBounds());


        CalculateNextOffsetFromThisComponent = newElement;
    }*/

}
