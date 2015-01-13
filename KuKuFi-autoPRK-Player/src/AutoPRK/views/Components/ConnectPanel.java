/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.views.Components;

import javax.swing.JPanel;

/**
 *
 * @author fbrzuzka
 */
public class ConnectPanel extends JPanel {

    private JPanel drumTrackPanel;
    private JPanel drumPartPanel;


    public ConnectPanel() {
        super();
        
    }
    
    
    
    
    
    
    
    
    
    public JPanel getDrumTrackPanel() {
        return drumTrackPanel;
    }

    public void setDrumTrackPanel(JPanel drumTrackPanel) {
        this.drumTrackPanel = drumTrackPanel;
    }

    public JPanel getDrumPartPanel() {
        return drumPartPanel;
    }

    public void setDrumPartPanel(JPanel drumPartPanel) {
        this.drumPartPanel = drumPartPanel;
    }
    
}
