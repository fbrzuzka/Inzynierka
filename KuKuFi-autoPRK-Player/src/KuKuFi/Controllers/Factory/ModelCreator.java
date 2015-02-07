package AutoPRK.Controllers.Factory;

import AutoPRK.Models.DrumPart;
import AutoPRK.Models.Containers.DrumPartList;
import AutoPRK.Models.DrumPartListSingleton;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import AutoPRK.Models.Containers.ProtocolListHashMap;
import AutoPRK.Models.Model;
import AutoPRK.Models.Containers.SingleDrumElementTimelineArray;
import com.sun.org.apache.xalan.internal.lib.ExsltMath;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import static javax.sound.midi.ShortMessage.NOTE_ON;

public class ModelCreator {

    static Model model = Model.instanceOf();

    public ModelCreator() {
        Model.protocolList = new ProtocolListHashMap();
       
      //  setAllTracks(model.sequenceOriginal);
        setResolutionInMs(Model.sequenceOriginal);
        echoInfoAboutModel();
        //parseTrackToArrayList(model.trackSelectedToGenerate);
        Model.protocolList = newParseToHashMap(model.trackSelectedToGenerate);

    }
    
    public DrumPartList addElementsToDrumTrack(){
        DrumPartList dpl = new DrumPartList();
        
        return dpl;
    }
    private ProtocolListHashMap newParseToHashMap(Track track){
        ProtocolListHashMap rc = new ProtocolListHashMap();
        
        Model.drumTrackElements  = findElementOfDrumKit(track);

        for (DrumPart drumKitElement : Model.drumTrackElements) {
            rc.put(drumKitElement.getPartName(), new SingleDrumElementTimelineArray(drumKitElement));
        }

        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);

           // double tickInMs = (event.getTick() * model.milis)/1000;
            
            int tempoTickInMs = (int)((event.getTick() * model.milis*1000)/1000);
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                if (sm.getCommand() == NOTE_ON) {

                    int key = sm.getData1();
                    int velocity = sm.getData2();
                    
                    System.out.print("@" + event.getTick() + " " + tempoTickInMs + " ");
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    System.out.println(" velocity: " + velocity + DrumPartListSingleton.getInstance().get(key).toString());

                    DrumPart drumPart = DrumPartListSingleton.getInstance().get(key);
                    rc.get(drumPart.getPartName()).add(tempoTickInMs);
                    
                }
            }
        }
        System.out.println();
        System.out.println("rc: " + rc.toString());
        return rc;
    
        
    }
    
    
    
    
    
   public static Sequence getSequenceFromMidiFile(File file) {

        Sequence rc = null;
        try {
            rc = MidiSystem.getSequence(file);
        } catch (InvalidMidiDataException | IOException ex) {
            Logger.getLogger(ModelCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rc;
    }
   
    public static void setAllTracks(Sequence seq) {
        for (Track track : seq.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                if (message instanceof MetaMessage) {
                    MetaMessage mm = (MetaMessage) message;

                    if (mm.getType() == 3) {
                        try {
                            byte[] data = mm.getData();
                            System.out.println("nazwa utworu: " + Arrays.toString(mm.getData()));

                            String str = new String(data, "ASCII");
                            Model.trackListOriginal.put(str, track);

                        } catch (UnsupportedEncodingException ex) {
                            Logger.getLogger(ModelCreator.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }


    private void setResolutionInMs(Sequence seq) {
        int longestTrack = -10;
        for (Track tr : seq.getTracks()) {
        }
        
        model.milis = model.sequenceOriginal.getMicrosecondLength() / (model.sequenceOriginal.getTickLength() * 1000.0);
    }

    private DrumPartList findElementOfDrumKit(Track track) {

        List<Integer> l = new ArrayList();
        DrumPartList listOfDrumKitElements = new DrumPartList();

        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);

            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                if (sm.getCommand() == NOTE_ON) {
                    int key = sm.getData1();
                    if (!l.contains(key)) {
                        l.add(key);
                        listOfDrumKitElements.add(DrumPartListSingleton.getInstance().get(key));
                    }
                }
            }
        }
        for (Integer  index : l) {
            System.out.println(DrumPartListSingleton.getInstance().get(index));
        }
        return listOfDrumKitElements;
    }

    private void echoMetaMessageInfo(MetaMessage mm) {

        int length = mm.getLength();
        int status = mm.getStatus();
        System.out.print(" status: " + status);
        System.out.print("length: " + length);
        System.out.print(" typ: " + mm.getType() + " data" + Arrays.toString(mm.getData()));

        if (mm.getType() == 88) {
            byte[] data = mm.getData();
            model.numerator = data[0];
            model.denominator = ExsltMath.power(2, data[1]);
            model.ticksPerMetronomClick = data[2];
        } 
    }

    private static void echoInfoAboutModel() {
        float timingType = model.sequenceOriginal.getDivisionType();

        System.out.println("timingType: " + timingType);
        if (timingType == Sequence.PPQ) {
            System.out.println("timingType: PPQ");
        } else if (timingType == Sequence.SMPTE_24) {
            System.out.println("timingType: SMPTE_24");
        } else if (timingType == Sequence.SMPTE_25) {
            System.out.println("timingType: SMPTE_25");
        } else if (timingType == Sequence.SMPTE_30) {
            System.out.println("timingType: SMPTE_30");
        } else if (timingType == Sequence.SMPTE_30DROP) {
            System.out.println("timingType: SMPTE_30DROP");
        }

        System.out.println("numerator: " + model.numerator);
        System.out.println("denominator: " + model.denominator);
        System.out.println("ticksPerMetronomClixk: " + model.ticksPerMetronomClick);

        System.out.println("sequence.getTickLength()  " + Model.sequenceOriginal.getTickLength());
        System.out.println("sequence.getMicrosecondLength()  " + Model.sequenceOriginal.getMicrosecondLength());
        System.out.println("length in second: " + (double) Model.sequenceOriginal.getMicrosecondLength() / 1000000);

        System.out.println("milis: " + model.milis);
        System.out.println("");
        System.out.println("");

    }
}
