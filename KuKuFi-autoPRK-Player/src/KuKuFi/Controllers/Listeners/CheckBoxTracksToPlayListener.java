/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers.Listeners;

import KuKuFi.Models.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.sound.midi.Track;
import javax.swing.JCheckBox;

/**
 *
 * @author fbrzuzka
 */
public class CheckBoxTracksToPlayListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        JCheckBox checkbox = (JCheckBox) ae.getSource();
        int trackNumber = getTruckNumberFromCheckBox(checkbox);
        Model.sequencePlayer.muteTrack(trackNumber, !checkbox.isSelected());
    }

    private int getTruckNumberFromCheckBox(JCheckBox checkbox) {
        int trackNumber = 0;
        String nameOfTrack = checkbox.getText();
        
        for (Map.Entry<String, Track> entrySet : Model.trackListOriginal.entrySet()) {
            String name = entrySet.getKey();
            if (name.equals(nameOfTrack)) {
                return trackNumber;
            }else{
                trackNumber++;
            }
        }
        return 0;

    }

}
