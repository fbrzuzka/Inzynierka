/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Controllers.ModelCreator;
import AutoPRK.Controllers.WindowController;
import AutoPRK.Models.Model;
import AutoPRK.views.mainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;

/**
 *
 * @author fbrzuzka
 */
public class OpenFileChooserListener implements ActionListener {

    private final Model model;
    private final mainWindow window;

    public OpenFileChooserListener() {

        this.window = mainWindow.window;
        this.model = Model.instanceOf();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        JFileChooser c = new JFileChooser();
        //s Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(mainWindow.window);
        if (rVal == JFileChooser.APPROVE_OPTION) {

            window.getSelectTrackComboBox().removeAllItems();
            Model.trackSelectedToGenerate = null;
            Model.trackListOriginal.clear();
            WindowController.getInstance().removeCheckBoxesfromChechBoxPanel();
            WindowController.getInstance().removeConnectRadioButtons();

            window.getMidiNameTextField().setText(c.getSelectedFile().getName());
            WindowController.getInstance().setEnablingOfStep2(true);
            model.midiFile = c.getSelectedFile();

            Model.sequenceOriginal = ModelCreator.getSequenceFromMidiFile(model.midiFile);

            ModelCreator.setAllTracks(Model.sequenceOriginal);

            WindowController.getInstance().setTracksInComboBox(Model.trackListOriginal);
            WindowController.getInstance().addSomeCheckBoxIntoCheckBoxPanel(Model.trackListOriginal);

        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            window.getSelectTrackComboBox().removeAllItems();
            window.getMidiNameTextField().setText("Select file ->");
        }

    }

}