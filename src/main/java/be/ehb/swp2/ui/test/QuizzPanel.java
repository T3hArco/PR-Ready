package be.ehb.swp2.ui.test;

/**
 * Created by Ibrahim on 02-12-15.
 */

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.jboss.logging.Logger;

import be.ehb.swp2.manager.QuizManager;

/**
 * Created by Ibrahim on 27-11-15.
 */

public class QuizzPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(QuizzPanel.class);
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private QuizManager quizzManager;




    /***
     * constructor
     *
     * @param frame
     *            frame
     * @param qm
     *            QuizzManager
     */
    public QuizzPanel( final JFrame frame, QuizManager qm){
        this.quizzManager = qm;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create an editor pane.
                JEditorPane editorPane = createEditorPane(frame);
                JScrollPane editorScrollPane = new JScrollPane(editorPane);
                editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                editorScrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                editorScrollPane.setMinimumSize(new Dimension(WIDTH, HEIGHT));
                add(editorScrollPane);
            }
        });
    }

    /***
     * create html page and listener
     *
     * @param parentFrame
     *            parent element
     * @return editor pane
     */

    private JEditorPane createEditorPane(final JFrame parentFrame) {
        //  JEditorPae bevat onze  HTML page
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setSize(WIDTH, HEIGHT);
        // We recup de html file
        java.net.URL helpURL = QuizzPanel.class.getResource("/html/index.html");
        if (helpURL != null) {
            try {
                // We sturen ons html pagina
                editorPane.setPage(helpURL);
                //  Deactiveren de echte link "action"
                // HTML
                ((HTMLEditorKit) editorPane.getEditorKit()).setAutoFormSubmission(false);
                //   We creeeren een listener om de click te ontvangen
                // SUBMIT
                editorPane.addHyperlinkListener(new HyperlinkListener() {
                    // @Override
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        String quizzName = null;
                        String filePath = null;
                        // We recup de html file
                        HTMLDocument doc = (HTMLDocument) editorPane.getDocument();

                        HTMLDocument.Iterator iter = doc.getIterator(HTML.Tag.INPUT);
                        while (iter.isValid()) {
                            AttributeSet s = iter.getAttributes();
                            //  Enkel input text of file interesseert ons
                            if ("text".equals(s.getAttribute(HTML.Attribute.TYPE))
                                    || "file".equals(s.getAttribute(HTML.Attribute.TYPE))) {
                                // cast in plaindocument
                                PlainDocument m = (PlainDocument) s.getAttribute(StyleConstants.ModelAttribute);
                                try {
                                    // we recup de waarde uit de  HTML
                                    String value = m.getText(0, m.getLength());

                                    if ("text".equals(s.getAttribute(HTML.Attribute.TYPE))) {
                                        quizzName = value;
                                    } else {
                                        filePath = value;
                                    }

                                } catch (BadLocationException e2) {
                                    LOGGER.error("problem getting value " + e.getDescription());
                                }
                                ;
                            }
                            iter.next();
                        }
                        if (null != quizzName && null != filePath) {
                            Integer id = quizzManager.addQuiz(quizzName, filePath, "Some description");
                            if (null != id) {
                                JOptionPane.showMessageDialog(null, new StringBuilder("The Quizz ").append(quizzName)
                                        .append(" has been created ! (id:").append(id).append(")").toString());
                                // Close window
                                parentFrame.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "The file couldn't be added");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "An issue occurred");
                        }
                    }
                });
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: index.html");
        }

        return editorPane;
    }
}
