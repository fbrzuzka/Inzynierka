/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners;

import KuKuFi_Pi.Controllers.Factory.ModelCreator;
import KuKuFi_Pi.Controllers.WindowController;
import KuKuFi_Pi.Controllers.WindowController_Pi;
import KuKuFi_Pi.MidiPlayer.SequencePlayer;
import KuKuFi_Pi.Models.JRadioButtonWithFile;
import KuKuFi_Pi.Models.Model;
import KuKuFi_Pi.views.MainWindow;
import KuKuFi_Pi.views.MainWindow_Pi;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 *
 * @author fbrzuzka
 */
public class GenerateButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        
        MainWindow_Pi.window_Pi.getGenerateButton().setEnabled(false);
        MainWindow_Pi.window_Pi.getGenerateButton().setText("loading...");
        System.out.println("Zmieniam sie na loading");
        

        Model.midiFile = getSelectedButtonText(MainWindow_Pi.window_Pi.getMidiFilesfButtonGroup()).getFile();

        Model.sequenceOriginal = ModelCreator.getSequenceFromMidiFile(Model.midiFile);
       
       
        setAllTracks(Model.sequenceOriginal);
        try{
            Model.trackSelectedToGenerate = findDrumTrack();        
            Model.sequencePlayer = new SequencePlayer(Model.sequenceOriginal);
       
            new ModelCreator();
            
            CardLayout cl = (CardLayout)(MainWindow_Pi.window_Pi.getMainPanel().getLayout());
            cl.show(MainWindow_Pi.window_Pi.getMainPanel(), "card2");
        
            
            
            WindowController_Pi.getInstance().addDrumTrackRadioButtons(Model.drumTrackElements);
            WindowController_Pi.getInstance().addDrumKitElementRadioButtons(Model.drumKitPartElements);


            MainWindow_Pi.window_Pi.getStep3Panel().repaint();
            MainWindow_Pi.window_Pi.pack();
            
            
            
            
            MainWindow_Pi.window_Pi.getGenerateButton().setEnabled(true);
            MainWindow_Pi.window_Pi.getGenerateButton().setText("generate");
            
            
        }catch(NoDrumTrackExeption noDrumException){
            System.out.println("");
            System.out.println("Nie znalazłem ścieżki perkusyjnej!!!!");
            System.out.println("");
        }
        
    }
    
    public Track findDrumTrack() throws NoDrumTrackExeption {
         for(String str : Model.trackListOriginal.keySet()){
             if(str.toUpperCase().contains("DRUM")){
                return Model.trackListOriginal.get(str);
             }
         }throw new NoDrumTrackExeption();
    }
    
     public void setAllTracks(Sequence seq) {
        for (Track track : seq.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                if (message instanceof MetaMessage) {
                    MetaMessage mm = (MetaMessage) message;

                    if (mm.getType() == 3) {
                        try {
                            byte[] data = mm.getData();

                            String str = new String(data, "ASCII");
                            System.out.println("  " + str);
                            Model.trackListOriginal.put(str, track);

                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(ModelCreator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
     
    public JRadioButtonWithFile getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                System.out.println("selected radio: " + button.getText());
                System.out.println("selected radio: " + ((JRadioButtonWithFile)button).getFile().getAbsolutePath());
                return (JRadioButtonWithFile)button;
            }
        }
        return null;
    }
}
