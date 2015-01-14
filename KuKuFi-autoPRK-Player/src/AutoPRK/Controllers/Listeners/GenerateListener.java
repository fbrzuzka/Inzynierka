/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Controllers.ModelCreator;
import AutoPRK.Controllers.PRKLogger;
import AutoPRK.Controllers.WindowController;
import AutoPRK.Models.Model;
import AutoPRK.views.mainWindow;
import AutoPRK.MidiPlayer.SequencePlayer;
import AutoPRK.Models.Containers.TrackMap;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Track;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

/**
 *
 * @author fbrzuzka
 */
public class GenerateListener implements ActionListener {

    public GenerateListener() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        //get selected element from jcombobox
        Model.trackSelectedToGenerate = getSelectedTrackFromComboBox();

        ModelCreator modelCreator = new ModelCreator();

        Model.sequencePlayer = new SequencePlayer(Model.sequenceOriginal);

        WindowController.getInstance().setEnablingOfStep3(true);
        WindowController.getInstance().echoFewImportantInfoOnInfoArea();
        WindowController.getInstance().setMaxOnMusicSlider((int) (Model.sequenceOriginal.getMicrosecondLength() / 1000000));

        
        WindowController.getInstance().addDrumTrackCheckBoxes(Model.drumTrackElements);
        WindowController.getInstance().addDrumElementRadioButtons(Model.drumPartElements);

    }

    private Track getSelectedTrackFromComboBox() {

        JComboBox cb = mainWindow.window.getSelectTrackComboBox();
        String trackName = (String) cb.getSelectedItem();
        PRKLogger.instance().logToInfoArea("wybrałeś ścieżkę: " + trackName);
        return Model.trackListOriginal.get(trackName);
    }

}
