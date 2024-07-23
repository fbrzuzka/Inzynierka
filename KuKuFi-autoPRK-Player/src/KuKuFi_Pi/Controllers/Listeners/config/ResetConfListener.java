/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners.config;

import KuKuFi_Pi.Controllers.WindowController;
import KuKuFi_Pi.Controllers.WindowController_Pi;
import KuKuFi_Pi.Models.Containers.ConnectConfiguration;
import KuKuFi_Pi.Models.Model;
import KuKuFi_Pi.views.MainWindow;
import KuKuFi_Pi.views.MainWindow_Pi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fbrzuzka
 */
public class ResetConfListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        WindowController_Pi.getInstance().removeConnectRadioButtons();
        Model.connectConfig = new ConnectConfiguration();
        
        WindowController_Pi.getInstance().addDrumTrackRadioButtons(Model.drumTrackElements);
        WindowController_Pi.getInstance().addDrumKitElementRadioButtons(Model.drumKitPartElements);
        
        WindowController_Pi.getInstance().setEnablingOfPlaying(Boolean.FALSE);
        WindowController_Pi.getInstance().setEnablingOfPause(Boolean.FALSE);
        WindowController_Pi.getInstance().setEnablingOfStop(Boolean.FALSE);
        
        MainWindow_Pi.window_Pi.getStep3Panel().repaint();
        
    }
    
}
