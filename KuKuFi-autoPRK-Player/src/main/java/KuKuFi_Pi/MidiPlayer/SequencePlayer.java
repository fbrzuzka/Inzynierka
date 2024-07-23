package KuKuFi_Pi.MidiPlayer;

import KuKuFi_Pi.Models.Model;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import org.apache.commons.lang3.StringUtils;

public class SequencePlayer {

    public static boolean useExternalSynth = false;
    private Sequence sequenceToPlay = null;
    private MidiDevice receivingDevice;
    private Sequencer sequencer1;
    public MusicSliderListener listner;
    private Thread thread;

    public SequencePlayer(Sequence seq) {

        this.sequenceToPlay = seq;

        try {
          //  receivingDevice = getReceivingDevice();
          //  receivingDevice.open();
            sequencer1 = MidiSystem.getSequencer(true);
            //Transmitter tx1 = sequencer1.getTransmitter();
           // Receiver rx1 = receivingDevice.getReceiver();
         //   tx1.setReceiver(rx1);

            sequencer1.open();
            sequencer1.setSequence(sequenceToPlay);

        } catch (MidiUnavailableException ex) {
            Logger.getLogger(SequencePlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(SequencePlayer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    public void callSlider() {

        int position = (int) (sequencer1.getTickPosition() * Model.instanceOf().tickToMilisRatio / 1000);
        listner.handleEvent(position);
    }

    public void muteTrack(int numberOfTrack, boolean mute) {
        sequencer1.setTrackMute(numberOfTrack, mute);
    }

    public void play() {
        sequencer1.start();
    }

    public void pause() {
        sequencer1.stop();
    }

    public void stop() {
        sequencer1.stop();
        sequencer1.setTickPosition(0);
    }

    private MidiDevice getReceivingDevice()
            throws MidiUnavailableException {
        for (MidiDevice.Info mdi : MidiSystem.getMidiDeviceInfo()) {
            MidiDevice dev = MidiSystem.getMidiDevice(mdi);
            if (dev.getMaxReceivers() != 0) {
                String lcName
                        = StringUtils.defaultString(mdi.getName())
                        .toLowerCase(Locale.ENGLISH);
                
                if (lcName.contains("midi")) {
                    return dev;
                }
            }
        }
        return null;
    }

    public void setTickPosition(long tickPosition) {
        sequencer1.setTickPosition(tickPosition);
    }

}
