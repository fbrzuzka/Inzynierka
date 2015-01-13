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
import AutoPRK.Controllers.Listeners.MusicSliderListener;
import AutoPRK.MidiPlayer.SequencePlayer;
import AutoPRK.Models.DrumPart;
import AutoPRK.Models.DrumPartList;
import AutoPRK.Models.Model;
import AutoPRK.Models.TrackMap;
import AutoPRK.views.mainWindow;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

/**
 *
 * @author fbrzuzka
 */
public class WindowController {

    private static WindowController windowController = null;
    private static mainWindow window = null;
    private Model model;
    private Thread thread;

    public static WindowController getInstance() {
        if (windowController == null) {
            windowController = new WindowController();
        }
        return windowController;
    }

    private WindowController() {
        this.model = Model.instanceOf();
        WindowController.window = mainWindow.window;
        configureListeners();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SequencePlayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (Model.sequencePlayer != null) {
                        try {
                            Model.sequencePlayer.callSlider();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });

        thread.start();
    }

    public void addDrumTrackCheckBoxes(DrumPartList drumTrackElements) {
        window.getConnectPanel().getDrumPartPanel().setLayout(new GridLayout(0, 1));
        window.getConnectPanel().getDrumTrackPanel().setLayout(new GridLayout(0, 1));
        try {
            for (DrumPart drumElement : drumTrackElements) {
                JCheckBox checkbox = new JCheckBox(drumElement.getPartName());
                checkbox.setSelected(false);
                window.getConnectPanel().getDrumTrackPanel().add(checkbox);
            }
        } catch (Exception e) {
        }

    }

    public void addDrumPartCheckBoxes(List<String> drumPartElements) {
     //   window.getConnectPanel().getDrumPartPanel().setLayout(new GridLayout(0, 1));
//        for (String drumElement : drumPartElements) {
//            JCheckBox checkbox = new JCheckBox(drumElement);
//            checkbox.setSelected(false);
//            window.getConnectPanel().getDrumPartPanel().add(checkbox);
//        }
        
        for (String drumElement : drumPartElements) {
            JRadioButton radio = new JRadioButton(drumElement);
            radio.setSelected(false);
            window.getButtonGroup1().add(radio);
        }
        
        

    }

    private void configureListeners() {
        window.getGenerateButton().addActionListener(new GenerateListener());
        window.getOpenButton().addActionListener(new OpenFileChooserListener());
        window.getPlayButton().addActionListener(new PlayButtonListener());
        window.getPauseButton().addActionListener(new PauseButtonListener());
        window.getStopButton().addActionListener(new StopButtonListener());
        MusicSliderListener msl = new MusicSliderListener();
        window.getMusicSlider().addChangeListener(msl);
        window.getMusicSlider().addMouseListener(msl);
    }

    public void setMaxOnMusicSlider(int max) {
        window.getMusicSlider().setMaximum(max);
        window.getMusicSlider().setPaintTicks(true);
        window.getMusicSlider().setMajorTickSpacing(30);
        window.getMusicSlider().setMinorTickSpacing(0);
        window.getMusicSlider().setPaintLabels(true);

        Model.sequencePlayer.listner = window.getMusicSlider();
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

    private void addListenerToCheckBox(JCheckBox checkbox) {
        checkbox.addActionListener(new CheckBoxTracksToPlayListener());
    }

    public void removeCheckBoxesfromChechBoxPanel() {

        Component[] components = window.getCheckBoxPanel().getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                window.getCheckBoxPanel().remove(component);
            }
        }

    }

    public void setEnablingOfStep1(Boolean enable) {
        window.getOpenButton().setEnabled(enable);
        window.getMidiNameTextField().setEnabled(enable);
    }

    public void setEnablingOfStep2(Boolean enable) {

        window.getSelectTrackComboBox().setEnabled(enable);
        window.getGenerateButton().setEnabled(enable);
    }

    public void setEnablingOfStep3(Boolean enable) {

        window.getPlayButton().setEnabled(enable);
        window.getPauseButton().setEnabled(enable);
        window.getStopButton().setEnabled(enable);
        window.getMusicSlider().setEnabled(enable);
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
}
