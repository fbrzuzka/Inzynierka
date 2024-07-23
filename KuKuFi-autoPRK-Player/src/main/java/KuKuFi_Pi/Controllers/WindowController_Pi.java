/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers;

import KuKuFi_Pi.Controllers.Listeners.EnableGenerateMidiButtonListener;
import KuKuFi_Pi.Controllers.Listeners.GenerateButtonListener;
import KuKuFi_Pi.Controllers.Listeners.GoBackButtonListener;
import KuKuFi_Pi.Controllers.Listeners.config.LoadConfListener;
import KuKuFi_Pi.Controllers.Listeners.config.ResetConfListener;
import KuKuFi_Pi.Controllers.Listeners.config.SaveConfListener;
import KuKuFi_Pi.Controllers.Listeners.play_pause_stop.PauseButtonListener;
import KuKuFi_Pi.Controllers.Listeners.play_pause_stop.PlayButtonListener;
import KuKuFi_Pi.Controllers.Listeners.play_pause_stop.StopButtonListener;
import KuKuFi_Pi.MidiPlayer.SequencePlayer;
import KuKuFi_Pi.Models.Containers.ConnectConfiguration;
import KuKuFi_Pi.Models.Containers.DrumPartList;
import KuKuFi_Pi.Models.DrumPart;
import KuKuFi_Pi.Models.JRadioButtonWithFile;
import KuKuFi_Pi.Models.Model;
import KuKuFi_Pi.views.Components.NamedJRadioButton;
import KuKuFi_Pi.views.MainWindow_Pi;
import org.apache.commons.lang3.SystemUtils;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * @author fbrzuzka
 */
public class WindowController_Pi {

    private static final String MIDI_FOLDER_NAME = "midi";
    private static String OS = "";

    private static WindowController_Pi windowController_Pi = null;
    private static MainWindow_Pi window_Pi = null;
    private Model model;
    private Thread thread;

    public static WindowController_Pi getInstance() {
        if (windowController_Pi == null) {
            windowController_Pi = new WindowController_Pi();
        }
        return windowController_Pi;
    }

    private WindowController_Pi() {
        this.model = Model.getInstance();
        WindowController_Pi.window_Pi = MainWindow_Pi.window_Pi;
        determineOS();
        List<File> midiFiles = listMidiFiles();
        printMidiFiles(midiFiles);
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

    public void echoFewImportantInfoOnInfoArea() {

        PRKLogger.instance().logToInfoArea("midi file name: " + model.midiFile.getName());
        PRKLogger.instance().logToInfoArea("metrum: " + 4 + "/" + 4);

        PRKLogger.instance().logToInfoArea("number of ticks:  " + Model.sequenceOriginal.getTickLength());
        PRKLogger.instance().logToInfoArea("lenght in ms:  " + Model.sequenceOriginal.getMicrosecondLength());
        PRKLogger.instance().logToInfoArea("length in second: " + (double) Model.sequenceOriginal.getMicrosecondLength() / 1000000);
        PRKLogger.instance().logToInfoArea("milis: " + model.tickToMilisRatio);
        PRKLogger.instance().logToInfoArea("");

    }

    private void configureListeners() {
        CardLayout cl = (CardLayout) (window_Pi.getMainPanel().getLayout());
        cl.show(window_Pi.getMainPanel(), "card1");
        window_Pi.getGenerateButton().addActionListener(new GenerateButtonListener());
        window_Pi.getBackTo1Button().addActionListener(new GoBackButtonListener());

        window_Pi.getPlayButton().addActionListener(new PlayButtonListener());
        window_Pi.getPauseButton().addActionListener(new PauseButtonListener());
        window_Pi.getStopButton().addActionListener(new StopButtonListener());

        window_Pi.getLoadConfButton().addActionListener(new LoadConfListener());
        window_Pi.getSaveConfButton().addActionListener(new SaveConfListener());
        window_Pi.getResetConfiguration().addActionListener(new ResetConfListener());

    }

    private List<File> listMidiFiles() {
        List<File> midiFiles = new ArrayList<>();
        List<File> midiFolderNames = new ArrayList<>();

        if (SystemUtils.IS_OS_WINDOWS) {
            midiFolderNames.addAll(Arrays.asList(File.listRoots()));
        } else if (SystemUtils.IS_OS_LINUX) {
            String homeDir = System.getProperty("user.home");
            midiFolderNames.add(new File(homeDir));
            if (SystemUtils.OS_ARCH.equals("amd64")) { // ubuntu
                System.out.println("files count: " + midiFolderNames.size());
            } else { // raspberry
                File usbFolder = new File("/media");
                midiFolderNames.addAll(Arrays.asList(usbFolder.listFiles()));
                System.out.println("files count: " + midiFolderNames.size());
            }
        } else {
            throw new RuntimeException("Unhandled os: " + SystemUtils.OS_NAME);
        }

        for (File file : midiFolderNames) {
            System.out.println("directory: " + file);
            System.out.println(file.isDirectory());
            if (file.isDirectory()) {
                try {
                    midiFiles.addAll(Arrays.asList(tryListMidi(file)));
                } catch (NullPointerException npe) {

                }
            }
        }
        return midiFiles;
    }

    private File[] tryListMidi(File file) {
        File midiFolder = new File(file, MIDI_FOLDER_NAME);

        File[] files = midiFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".mid");
            }
        });
        if (files != null) {
            for (File f : files) {
                System.out.println("Drive: " + f.getAbsolutePath());
            }
        }
        return files;
    }

    private void determineOS() {
        OS = System.getProperty("os.name");
        System.out.println("os: " + OS);
    }

    private void printMidiFiles(List<File> midiFiles) {
        JPanel midiFilesPanel = window_Pi.getJPanelMidiFiles();

        midiFilesPanel.setLayout(new GridLayout(0, 1));
        for (File f : midiFiles) {
            JRadioButtonWithFile radio = new JRadioButtonWithFile(f);
            radio.setSelected(false);
            radio.setFont(new Font("Arial", 0, 24));
            radio.addActionListener(new EnableGenerateMidiButtonListener());
            midiFilesPanel.add(radio);
            window_Pi.getMidiFilesfButtonGroup().add(radio);
        }
    }

    public void addDrumTrackRadioButtons(DrumPartList drumTrackElements) {
        window_Pi.getConnectPanel().getDrumTrackPanel().setLayout(new GridLayout(0, 1));
        ArrayList<NamedJRadioButton> indexes = new ArrayList<>();
        HashMap<Integer, NamedJRadioButton> namedJRadioButtons = new HashMap<>();

        try {
            for (DrumPart drumElement : drumTrackElements) {
                NamedJRadioButton radio = new NamedJRadioButton(drumElement.getPartName(), Model.protocolList.get(drumElement.getPartName()).size());
                radio.setName(drumElement.getPartName());
                radio.setSelected(false);
                radio.setFont(new Font("Arial", 0, 18));
                radio.addActionListener(window_Pi.getConnectPanel());
                namedJRadioButtons.put(radio.getHits(), radio);
            }
            Set<Integer> set = namedJRadioButtons.keySet();
            TreeSet<Integer> treeSet = new TreeSet(set);
            Iterator iterator = treeSet.descendingIterator();

            while (iterator.hasNext()) {
                Integer i = (Integer) iterator.next();
                window_Pi.getConnectPanel().getDrumTrackPanel().add(namedJRadioButtons.get(i));
                window_Pi.getConnectPanel().getDrumTrackPanel().getGroup().add(namedJRadioButtons.get(i));
            }
        } catch (Exception e) {
        }
    }

    public void addDrumKitElementRadioButtons(List<String> drumPartElements) {
        window_Pi.getConnectPanel().getDrumKitElementPanel().setLayout(new GridLayout(0, 1));
        for (String drumElement : drumPartElements) {
            JRadioButton radio = new JRadioButton(drumElement);
            radio.setSelected(false);
            radio.setFont(new Font("Arial", 0, 18));
            window_Pi.getConnectPanel().getDrumKitElementPanel().add(radio);
            window_Pi.getConnectPanel().getDrumKitElementPanel().getGroup().add(radio);
            radio.addActionListener(window_Pi.getConnectPanel());
        }
    }

    public void removeConnectRadioButtons() {
        window_Pi.getConnectPanel().clear();
        Model.connectConfig = new ConnectConfiguration();
        //   window.getConnectPanel().repaint();
    }

    public void setEnablingOfPlaying(boolean b) {
        window_Pi.getPlayButton().setEnabled(b);
    }

    public void setEnablingOfPause(boolean b) {
        window_Pi.getPauseButton().setEnabled(b);
    }

    public void setEnablingOfStop(boolean b) {
        window_Pi.getStopButton().setEnabled(b);
    }

    /**
     * call to shutdown pi4j
     */
    public void disposePi4j() {
        model.getPinoutService().shutdown();
    }
}
