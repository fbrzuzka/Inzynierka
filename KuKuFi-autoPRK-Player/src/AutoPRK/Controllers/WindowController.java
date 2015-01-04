/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers;

import AutoPRK.Controllers.Listeners.CheckBoxTracksToPlayListener;
import AutoPRK.Controllers.Listeners.GenerateListener;
import AutoPRK.Controllers.Listeners.OpenFileChooserListener;
import AutoPRK.Controllers.Listeners.PauseButtonListener;
import AutoPRK.Controllers.Listeners.PlayButtonListener;
import AutoPRK.Controllers.Listeners.StopButtonListener;
import AutoPRK.Exceptions.InitlializeWindowControllerException;
import AutoPRK.Models.Model;
import AutoPRK.Models.TrackMap;
import AutoPRK.views.mainWindow;
import java.awt.Component;
import java.awt.GridLayout;
import javax.sound.midi.Track;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 *
 * @author fbrzuzka
 */
public class WindowController {

    private static WindowController windowController= null;
    private static mainWindow window = null;
    private Model model;
    
    public static void initializeController(mainWindow window){
        if(windowController == null){
            
        }
    }
    public static WindowController getInstance(){
        if(windowController == null){
           windowController = new WindowController();
        }
        return windowController;
    }


    private WindowController() {
        this.model = Model.instanceOf();
        this.window = mainWindow.window;
        configureListeners();
    }

    private void configureListeners() {
        window.getGenerateButton().addActionListener(new GenerateListener());
        window.getOpenButton().addActionListener(new OpenFileChooserListener());
        window.getPlayButton().addActionListener(new PlayButtonListener());
        window.getPauseButton().addActionListener(new PauseButtonListener());
        window.getStopButton().addActionListener(new StopButtonListener());
        
    }

    public void echoFewImportantInfoOnInfoArea() {

        PRKLogger.instance().logToInfoArea("midi file name: " + model.midiFile.getName());
        PRKLogger.instance().logToInfoArea("metrum: " + model.numerator + "/" + model.denominator);
        
        PRKLogger.instance().logToInfoArea("number of ticks:  " + Model.sequenceOriginal.getTickLength());
        PRKLogger.instance().logToInfoArea("lenght in ms:  " + Model.sequenceOriginal.getMicrosecondLength());
        PRKLogger.instance().logToInfoArea("length in second: " + (double) Model.sequenceOriginal.getMicrosecondLength() / 1000000);
        PRKLogger.instance().logToInfoArea("??sequence.getResolution()??: " + model.PPQ);
        PRKLogger.instance().logToInfoArea("minimal Note Length in ms: " + model.minimalNoteMsLength);
        PRKLogger.instance().logToInfoArea("tempo: " + model.tempo);
        PRKLogger.instance().logToInfoArea("milis: " + model.milis);
        PRKLogger.instance().logToInfoArea("");

    }

    public void setTracksInComboBox(TrackMap trackMap) {
        for (String name : trackMap.keySet()) {
            window.getSelectTrackComboBox().addItem(name);
        }
        window.getSelectTrackComboBox().setEnabled(true);

    }
   
    
    public void addSomeCheckBoxIntoCheckBoxPanel(TrackMap trackMap) {
         window.getCheckBoxPanel().setLayout(new GridLayout(0, 1));
        for (String name : trackMap.keySet()) {
            JCheckBox checkbox = new JCheckBox(name);
            checkbox.setSelected(true);
            addListenerToCheckBox(checkbox);
            window.getCheckBoxPanel().add(checkbox);
         //   window.getSelectTrackComboBox().addItem(name);
        }
        window.getCheckBoxPanel().repaint();
        window.getPlayPanel().setEnabled(true);

    }
    
    private void addListenerToCheckBox(JCheckBox checkbox){
        checkbox.addActionListener(new CheckBoxTracksToPlayListener());
    }

    public void removeCheckBoxesfromChechBoxPanel() {
        
        Component[] components = window.getCheckBoxPanel().getComponents();
        for (Component component : components) {
            if(component instanceof JCheckBox){
                window.getCheckBoxPanel().remove(component);
            }
        }
        
    }
    
    public void setEnablingOfStep1(Boolean enable){
        window.getOpenButton().setEnabled(enable);
        window.getMidiNameTextField().setEnabled(enable);
    }
    
    public void setEnablingOfStep2(Boolean enable){
        
        window.getSelectTrackComboBox().setEnabled(enable);
        window.getGenerateButton().setEnabled(enable);
    }
    
    public void setEnablingOfStep3(Boolean enable){
        
        window.getPlayButton().setEnabled(enable);
        window.getPauseButton().setEnabled(enable);
        window.getStopButton().setEnabled(enable);
    }

}
