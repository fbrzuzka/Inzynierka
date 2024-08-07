/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners.oldListeners;

import KuKuFi_Pi.Controllers.Factory.ModelCreator;
import KuKuFi_Pi.Controllers.PRKLogger;
import KuKuFi_Pi.Controllers.WindowController;
import KuKuFi_Pi.Models.Model;
import KuKuFi_Pi.views.MainWindow;
import KuKuFi_Pi.MidiPlayer.SequencePlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.midi.Track;
import javax.swing.JComboBox;

/**
 *
 * @author fbrzuzka
 */
public class GenerateListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {

        //get selected element from jcombobox
        Model.trackSelectedToGenerate = getSelectedTrackFromComboBox();

        ModelCreator modelCreator = new ModelCreator();

        Model.sequencePlayer = new SequencePlayer(Model.sequenceOriginal);

        WindowController.getInstance().setEnablingOfStep3(true);
        WindowController.getInstance().echoFewImportantInfoOnInfoArea();
        WindowController.getInstance().setMaxOnMusicSlider((int) (Model.sequenceOriginal.getMicrosecondLength() / 1000000));
        
        
        WindowController.getInstance().addDrumTrackRadioButtons(Model.drumTrackElements);
        WindowController.getInstance().addDrumKitElementRadioButtons(Model.drumKitPartElements);
        
        
        MainWindow.window.getStep3Panel().repaint();
        MainWindow.window.pack();


    }

    private Track getSelectedTrackFromComboBox() {

        JComboBox cb = MainWindow.window.getSelectTrackComboBox();
        String trackName = (String) cb.getSelectedItem();
        PRKLogger.instance().logToInfoArea("wybrałeś ścieżkę: " + trackName);
        return Model.trackListOriginal.get(trackName);
    }

}
