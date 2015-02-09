/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers.Listeners;

import KuKuFi.Controllers.Factory.ModelCreator;
import KuKuFi.Controllers.WindowController;
import KuKuFi.Models.Model;
import KuKuFi.views.MainWindow;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author fbrzuzka
 */
public class OpenFileChooserListener implements ActionListener {

    private final Model model;
    private final MainWindow window;

    public OpenFileChooserListener() {

        this.window = MainWindow.window;
        this.model = Model.instanceOf();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        JFileChooser c = new JFileChooser();
        c.setPreferredSize(new Dimension(800, 600));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("midi files only :)", "mid", "midi");
        c.setFileFilter(filter);
        
        int rVal = c.showOpenDialog(MainWindow.window);
        if (rVal == JFileChooser.APPROVE_OPTION) {

            window.getSelectTrackComboBox().removeAllItems();
            Model.trackSelectedToGenerate = null;
            Model.trackListOriginal.clear();
            WindowController.getInstance().removeCheckBoxesfromChechBoxPanel();
            WindowController.getInstance().removeConnectRadioButtons();

            window.getMidiNameTextField().setText(c.getSelectedFile().getName());
            model.midiFile = c.getSelectedFile();

            Model.sequenceOriginal = ModelCreator.getSequenceFromMidiFile(model.midiFile);

            ModelCreator.setAllTracks(Model.sequenceOriginal);

            WindowController.getInstance().addSomeCheckBoxIntoCheckBoxPanel(Model.trackListOriginal);
            WindowController.getInstance().setTracksInComboBox(Model.trackListOriginal);
            WindowController.getInstance().setDrumTrackAsSelected();
            
            
            WindowController.getInstance().setEnablingOfStep2(true);
            WindowController.getInstance().setEnablingOfStep3(false);
            WindowController.getInstance().setEnablingOfStep4(false);

        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            window.getSelectTrackComboBox().removeAllItems();
            window.getMidiNameTextField().setText("Select file ->");

            

        }

    }

}
