/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.views.Components;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author fbrzuzka
 */
public class DrumTrackPanelBase extends JPanel {

    private ButtonGroup group;

    public DrumTrackPanelBase() {
        group = new ButtonGroup();
    }

    public ButtonGroup getGroup() {
        return group;
    }

    public void moveRadioToUpOnPanel(JRadioButton radio) {
        this.setComponentZOrder(radio, 0);
        radio.setEnabled(false);
        group.clearSelection();
    }

    void clear() {

        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            group.remove(button);
        }

        for (Component component : this.getComponents()) {
            if (component instanceof JRadioButton) {
                component.setVisible(false);
                this.remove(component);
            }
        }
        super.repaint();
        this.repaint();

    }

    void addListener(ActionListener al) {

        for (Component component : this.getComponents()) {
            if (component instanceof JRadioButton) {
                ((JRadioButton) component).addActionListener(al);
            }
        }

    }

    public JRadioButton findByName(String name) {
        JRadioButton rb = null;
        for (Component component : this.getComponents()) {
            if (component instanceof JRadioButton) {
                JRadioButton radio = (JRadioButton) component;
                if (radio.getText().equals(name)) {
                    rb = radio;
                }

            }
        }

        return rb;
    }

}
