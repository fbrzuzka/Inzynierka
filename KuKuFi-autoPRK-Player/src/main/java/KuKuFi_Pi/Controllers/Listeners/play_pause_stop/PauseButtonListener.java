/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners.play_pause_stop;

import KuKuFi_Pi.Controllers.WindowController;
import KuKuFi_Pi.Controllers.WindowController_Pi;
import KuKuFi_Pi.Models.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fbrzuzka
 */
public class PauseButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent ae) {
        //Model.sequencePlayer.pause();
        
        WindowController_Pi.getInstance().setEnablingOfPlaying(Boolean.TRUE);
        WindowController_Pi.getInstance().setEnablingOfPause(Boolean.FALSE);
        WindowController_Pi.getInstance().setEnablingOfStop(Boolean.TRUE);

    }
    
}