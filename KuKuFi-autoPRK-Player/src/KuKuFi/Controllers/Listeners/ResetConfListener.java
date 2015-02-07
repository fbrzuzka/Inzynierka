/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers.Listeners;

import KuKuFi.Controllers.WindowController;
import KuKuFi.Models.Containers.ConnectConfiguration;
import KuKuFi.Models.Model;
import KuKuFi.views.MainWindow;
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
        
        MainWindow.window.getStep3Panel().repaint();
        
    }
    
}
