/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Controllers.Factory.ModelCreator;
import AutoPRK.Controllers.PRKLogger;
import AutoPRK.Controllers.WindowController;
import AutoPRK.Models.Model;
import AutoPRK.views.MainWindow;
import AutoPRK.MidiPlayer.SequencePlayer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.midi.Track;
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
