package be.ehb.swp2.ui.test;

/**
 * Created by Christophe on 3/11/2015.
 */

import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @Todo insert comments
 */
public class HintTextAreaUI extends BasicTextAreaUI implements FocusListener {

    /**
     * Hint voor een quiz
     */
    private String hint;

    /**
     * Hide on focus
     */
    private boolean hideOnFocus;

    /**
     * Styling option
     */
    private Color color;

    /**
     * Hint
     *
     * @param hint hint
     */
    public HintTextAreaUI(String hint) {
        this(hint, false);
    }

    public HintTextAreaUI(String hint, boolean hideOnFocus) {
        this(hint, hideOnFocus, null);
    }

    public HintTextAreaUI(String hint, boolean hideOnFocus, Color color) {
        this.hint = hint;
        this.hideOnFocus = hideOnFocus;
        this.color = color;
    }

    /**
     * Geeft de kleur terug
     *
     * @return Color color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Zet de kleur
     *
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    /**
     * Repaint window
     */
    private void repaint() {
        if (getComponent() != null) {
            getComponent().repaint();
        }
    }

    /**
     * Getter voor hide on focus
     *
     * @return boolean
     */
    public boolean isHideOnFocus() {
        return hideOnFocus;
    }

    /**
     * @param hideOnFocus
     */
    public void setHideOnFocus(boolean hideOnFocus) {
        this.hideOnFocus = hideOnFocus;
        repaint();
    }

    /**
     * Geeft een hint weer
     *
     * @return String
     */
    public String getHint() {
        return hint;
    }

    /**
     * Zet de hint
     *
     * @param hint String
     */
    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    @Override
    protected void paintSafely(Graphics g) {
        super.paintSafely(g);
        JTextComponent comp = getComponent();
        if (hint != null && comp.getText().length() == 0 && (!(hideOnFocus && comp.hasFocus()))) {
            if (color != null) {
                g.setColor(color);
            } else {
                g.setColor(comp.getForeground().brighter().brighter().brighter());
            }
            int padding = (comp.getHeight() - comp.getFont().getSize()) / 2;
            g.drawString(hint, 2, comp.getHeight() - padding - 1);
        }
    }

    public void focusGained(FocusEvent e) {
        if (hideOnFocus) repaint();

    }

    public void focusLost(FocusEvent e) {
        if (hideOnFocus) repaint();
    }


    protected void installListeners() {
        super.installListeners();
        getComponent().addFocusListener(this);
    }

    protected void uninstallListeners() {
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
}