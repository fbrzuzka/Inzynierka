/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.views.Components;

import AutoPRK.Controllers.PRKLogger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author fbrzuzka
 */
public class ConnectPanel extends JPanel implements ActionListener{

    private DrumTrackPanelBase drumTrackPanel;
    private DrumTrackPanelBase drumElementtPanel;


    public ConnectPanel() {
        super();
        
    }
    
    
    public void clear(){
        drumElementtPanel.clear();
        drumTrackPanel.clear();
    }
    
    
    public ActionListener getActionListener(){
        
        return this.getActionListener();
    }
    
    
    
    public DrumTrackPanelBase getDrumTrackPanel() {
        return drumTrackPanel;
    }

    public void setDrumTrackPanel(DrumTrackPanelBase drumTrackPanel) {
        this.drumTrackPanel = drumTrackPanel;
    }

    public DrumTrackPanelBase getDrumElementPanel() {
        return drumElementtPanel;
    }

    public void setDrumElementPanel(DrumTrackPanelBase drumPartPanel) {
        this.drumElementtPanel = drumPartPanel;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        PRKLogger.instance().logToInfoArea("lajfljhdsfljshgdlfksgd");
    }
    
}
