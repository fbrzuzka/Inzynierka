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
import AutoPRK.Controllers.Listeners.LoadConfListener;
import AutoPRK.Controllers.Listeners.ResetConfListener;
import AutoPRK.Controllers.Listeners.SaveConfListener;
import AutoPRK.MidiPlayer.SequencePlayer;
import AutoPRK.Models.Containers.ConnectConfiguration;
import AutoPRK.Models.DrumPart;
import AutoPRK.Models.Containers.DrumPartList;
import AutoPRK.Models.Model;
import AutoPRK.Models.Containers.TrackMap;
import AutoPRK.views.MainWindow;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/**
 *
 * @author fbrzuzka
 */
public class WindowController {

    private static WindowController windowController = null;
    private static MainWindow window = null;
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
        WindowController.window = MainWindow.window;
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

        setEnablingOfStep2(false);
        setEnablingOfStep3(false);
        setEnablingOfStep4(false);
    }

    public void addDrumTrackRadioButtons(DrumPartList drumTrackElements) {
        window.getConnectPanel().getDrumTrackPanel().setLayout(new GridLayout(0, 1));
        try {
            for (DrumPart drumElement : drumTrackElements) {
                JRadioButton radio = new JRadioButton(drumElement.getPartName());
                radio.setSelected(false);
                window.getConnectPanel().getDrumTrackPanel().add(radio);
                window.getConnectPanel().getDrumTrackPanel().getGroup().add(radio);
                radio.addActionListener(window.getConnectPanel());
                PRKLogger.instance().logToInfoArea(Integer.toString(window.getConnectPanel().getDrumTrackPanel().getComponentZOrder(radio)));

            }
        } catch (Exception e) {
        }

    }

    public void removeDrumTrackRadioButtons() {

    }

    public void removeDrumKitElementRadioButtons() {

    }

    public void addDrumKitElementRadioButtons(List<String> drumPartElements) {
        window.getConnectPanel().getDrumKitElementPanel().setLayout(new GridLayout(0, 1));
        for (String drumElement : drumPartElements) {
            JRadioButton radio = new JRadioButton(drumElement);
            radio.setSelected(false);
            window.getConnectPanel().getDrumKitElementPanel().add(radio);
            window.getConnectPanel().getDrumKitElementPanel().getGroup().add(radio);
            radio.addActionListener(window.getConnectPanel());
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
        window.getLoadConfButton().addActionListener(new LoadConfListener());
        window.getSaveConfButton().addActionListener(new SaveConfListener());
        window.getResetConfiguration().addActionListener(new ResetConfListener());

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
            checkbox.setEnabled(false);
            addListenerToCheckBox(checkbox);
            window.getCheckBoxPanel().add(checkbox);
            //   window.getSelectTrackComboBox().addItem(name);  //mie wiem co z tym ale chyba do wywalenia/
        }
        window.getCheckBoxPanel().repaint();
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

    public void removeConnectRadioButtons() {
        window.getConnectPanel().clear();  //wyłaczyłem bo za bardzo się usuwało.
        Model.connectConfig = new ConnectConfiguration();
        //   window.getConnectPanel().repaint();
    }

    public void setEnablingOfStep1(Boolean enable) {
        setEnableInContainer(window.getStep1Panel(), enable);
    }

    public void setEnablingOfStep2(Boolean enable) {
        setEnableInContainer(window.getStep2Panel(), enable);
    }

    public void setEnablingOfStep3(Boolean enable) {
        setEnableInContainer(window.getStep3Panel(), enable);
    }

    public void setEnablingOfStep4(Boolean enable) {
        setEnableInContainer(window.getStep4Panel(), enable);
    }

    public void setEnableInContainer(Container container, Boolean enable) {
        for (Component component : container.getComponents()) {
            if (component instanceof Container) {
                Container container2 = (Container) component;
                setEnableInContainer(container2, enable);
            }
            component.setEnabled(enable);
        }
    }

    public void echoFewImportantInfoOnInfoArea() {

        PRKLogger.instance().logToInfoArea("midi file name: " + model.midiFile.getName());
        PRKLogger.instance().logToInfoArea("metrum: " + model.numerator + "/" + model.denominator);

        PRKLogger.instance().logToInfoArea("number of ticks:  " + Model.sequenceOriginal.getTickLength());
        PRKLogger.instance().logToInfoArea("lenght in ms:  " + Model.sequenceOriginal.getMicrosecondLength());
        PRKLogger.instance().logToInfoArea("length in second: " + (double) Model.sequenceOriginal.getMicrosecondLength() / 1000000);
        PRKLogger.instance().logToInfoArea("milis: " + model.milis);
        PRKLogger.instance().logToInfoArea("");

    }

    public void setDrumTrackAsSelected() {
         for(int i=0 ; i < window.getSelectTrackComboBox().getItemCount() ; i++){
             if(window.getSelectTrackComboBox().getItemAt(i).toString().toUpperCase().contains("DRUM")){
                 window.getSelectTrackComboBox().setSelectedIndex(i);
                 break;
             }
         }
    }

    public void showWarningDialog() {
        JOptionPane.showMessageDialog(window,
                    "autoPRK is not connected to USB port or port is in use.\n Please unplug autoPRK from USB and then plug it again.",
                    "Inane warning",
                    JOptionPane.WARNING_MESSAGE);
    }

}
