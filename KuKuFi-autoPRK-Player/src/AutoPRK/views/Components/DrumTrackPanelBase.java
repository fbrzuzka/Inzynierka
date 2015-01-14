/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.views.Components;

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
public class DrumTrackPanelBase extends JPanel{

    private ButtonGroup group;
    
    public DrumTrackPanelBase() {
        group = new ButtonGroup();
    }

    

    public ButtonGroup getGroup() {
        return group;
    }

    void clear() {
        
        for (Component component : this.getComponents()) {
            this.remove(component);
        }
        
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            
            
            group.remove(button);
        }
        
    }
    
    void addListener(ActionListener al) {
        
        for (Component component : this.getComponents()) {
            if(component instanceof JRadioButton){
                ((JRadioButton)component).addActionListener(al);
            }
        }
        
    }
    
}
