/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Controllers.WindowController;
import AutoPRK.Models.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fbrzuzka
 */
public class StopButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent ae) {
        Model.sequencePlayer.stop();
        
        WindowController.getInstance().setEnablingOfStep1(Boolean.TRUE);
        WindowController.getInstance().setEnablingOfStep2(Boolean.TRUE);
        
    }
    
}