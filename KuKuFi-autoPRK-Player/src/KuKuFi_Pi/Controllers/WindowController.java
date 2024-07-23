/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers;

import KuKuFi_Pi.Controllers.Listeners.oldListeners.CheckBoxTracksToPlayListener;
import KuKuFi_Pi.Controllers.Listeners.oldListeners.GenerateListener;
import KuKuFi_Pi.Controllers.Listeners.oldListeners.OpenFileChooserListener;
import KuKuFi_Pi.Controllers.Listeners.play_pause_stop.PauseButtonListener;
import KuKuFi_Pi.Controllers.Listeners.play_pause_stop.PlayButtonListener;
import KuKuFi_Pi.Controllers.Listeners.play_pause_stop.StopButtonListener;
import KuKuFi_Pi.Controllers.Listeners.oldListeners.MusicSliderListener;
import KuKuFi_Pi.Controllers.Listeners.config.LoadConfListener;
import KuKuFi_Pi.Controllers.Listeners.config.ResetConfListener;
import KuKuFi_Pi.Controllers.Listeners.config.SaveConfListener;
import KuKuFi_Pi.MidiPlayer.SequencePlayer;
import KuKuFi_Pi.Models.Containers.ConnectConfiguration;
import KuKuFi_Pi.Models.DrumPart;
import KuKuFi_Pi.Models.Containers.DrumPartList;
import KuKuFi_Pi.Models.Model;
import KuKuFi_Pi.Models.Containers.TrackMap;
import KuKuFi_Pi.views.Components.NamedJRadioButton;
import KuKuFi_Pi.views.MainWindow;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
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
                NamedJRadioButton radio = new NamedJRadioButton(drumElement.getPartName(), Model.protocolList.get(drumElement.getPartName()).size());
                radio.setName(drumElement.getPartName());
                radio.setSelected(false);
                window.getConnectPanel().getDrumTrackPanel().add(radio);
                window.getConnectPanel().getDrumTrackPanel().getGroup().add(radio);
                radio.addActionListener(window.getConnectPanel());
              //  PRKLogger.instance().logToInfoArea(Integer.toString(window.getConnectPanel().getDrumTrackPanel().getComponentZOrder(radio)));

            }
        } catch (Exception e) {}

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
        window.getMusicSlider().addMouseListener(new MusicSliderListener());
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
        window.getConnectPanel().clear(); 
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
        PRKLogger.instance().logToInfoArea("metrum: " + 4 + "/" + 4);

        PRKLogger.instance().logToInfoArea("number of ticks:  " + Model.sequenceOriginal.getTickLength());
        PRKLogger.instance().logToInfoArea("lenght in ms:  " + Model.sequenceOriginal.getMicrosecondLength());
        PRKLogger.instance().logToInfoArea("length in second: " + (double) Model.sequenceOriginal.getMicrosecondLength() / 1000000);
        PRKLogger.instance().logToInfoArea("milis: " + model.tickToMilisRatio);
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
