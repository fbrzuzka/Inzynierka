/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Controllers.WindowController;
import AutoPRK.Models.Containers.ConnectConfiguration;
import AutoPRK.Models.Model;
import AutoPRK.views.mainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fbrzuzka
 */
public class ResetConfListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
        WindowController.getInstance().removeConnectRadioButtons();
        Model.connectConfig = new ConnectConfiguration();
        
        WindowController.getInstance().addDrumTrackRadioButtons(Model.drumTrackElements);
        WindowController.getInstance().addDrumKitElementRadioButtons(Model.drumKitPartElements);
        
        WindowController.getInstance().setEnablingOfStep4(false);
        
        mainWindow.window.getStep3Panel().repaint();
        
    }
    
}
