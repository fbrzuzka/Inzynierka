package AutoPRK.Controllers;

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
       // setSDontRememberWhat(Model.trackSelectedToGenerate);
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
    private void setSDontRememberWhat(Track track) {

        Double min = model.minimalNoteMsLength;
        double oldTicksms = 0;
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);

            double tickInMs = Math.round(event.getTick() * model.milis);

            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                if (sm.getCommand() == NOTE_ON) {

                    if (tickInMs - oldTicksms < min && tickInMs - oldTicksms != 0) {
                        min = tickInMs - oldTicksms;
                        System.out.println("min: " + min + " @" + event.getTick());
                    }

                    oldTicksms = tickInMs;
                }

            } else if (message instanceof MetaMessage) {
              // MetaMessage  mm = (MetaMessage) message;
                //echoMetaMessageInfo(mm);

            }
        }
        model.minimalNoteMsLength = min;
        model.tempo = 125.0 / min;
        System.out.println();

    }

//    private void initTimelineWithBlankElements(SingleDrumElementTimelineArray timeLine, long microSecLength) {
//        int stop = (int) (Math.ceil(microSecLength * model.tempo / 1000000));
//        for (int i = 0; i < stop; i++) {
//            timeLine.add(new ByteNote(ByteNote.EMPTY_BYTE));
//        }
//    }
    /*
    private ProtocolListHashMap parseTrackToArrayList(Track track) {
        ProtocolListHashMap rc = new ProtocolListHashMap();
        List<DrumPart> listOfDrumKitElements = findElementOfDrumKit(track);

        for (DrumPart drumKitElement : listOfDrumKitElements) {
            SingleDrumElementTimelineArray elementTimeLine = new SingleDrumElementTimelineArray(drumKitElement);
            initTimelineWithBlankElements(elementTimeLine, Model.sequenceOriginal.getMicrosecondLength());
            rc.put(drumKitElement, elementTimeLine);
        }

        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);

            double tickInMs = (event.getTick() * model.milis);
            double tempoTickInMs = tickInMs * model.tempo;
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                if (sm.getCommand() == NOTE_ON) {

                    int key = sm.getData1();
                    int velocity = sm.getData2();
                    
                    System.out.print("@" + event.getTick() + " " + tempoTickInMs + " ");
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    System.out.println(" velocity: " + velocity + DrumPartListSingleton.getInstance().get(key).toString());

                    int indexOfNotetoSet = (int) (tempoTickInMs / 1000);

                    DrumPart drumPart;
                    drumPart = DrumPartListSingleton.getInstance().get(key);

                    ByteNote noteToSet = rc.get(drumPart).get(indexOfNotetoSet);
                    noteToSet.setByteNote(event);

                    rc.get(drumPart).set(indexOfNotetoSet, noteToSet);
                }
            }
        }
        System.out.println();
        System.out.println("rc: " + rc.toArduino(Model.limitOfTrackLength));
        return rc;
    }
    */
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
        //System.out.println("lista: " + l);
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
            model.ticksPerMetronomClixk = data[2];

            //    System.out.println("PPQN: " + PPQN);
        } else if (mm.getType() == 81) {
            byte[] data = mm.getData();
            Integer MPQN0 = data[0] > 0 ? data[0] : data[0] & 0xFF;
            Integer MPQN1 = data[1] > 0 ? data[1] : data[1] & 0xFF;
            Integer MPQN2 = data[2] > 0 ? data[2] : data[2] & 0xFF;

            System.out.println("MPQN: " + MPQN0 + " " + MPQN1 + " " + MPQN2);
            String bpm = MPQN0.toString() + MPQN1.toString() + MPQN2.toString();
            model.BPM = 60000000 / Integer.parseInt(bpm);
            System.out.println("Real BPM: " + model.BPM);

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
        System.out.println("ticksPerMetronomClixk: " + model.ticksPerMetronomClixk);

        System.out.println("sequence.getTickLength()  " + model.sequenceOriginal.getTickLength());
        System.out.println("sequence.getMicrosecondLength()  " + model.sequenceOriginal.getMicrosecondLength());
        System.out.println("length in second: " + (double) model.sequenceOriginal.getMicrosecondLength() / 1000000);
        System.out.println("sequence.getResolution(): " + model.PPQ);
        System.out.println("minimal Note Length in ms: " + model.minimalNoteMsLength);
        System.out.println("tempo: " + model.tempo);

        System.out.println("milis: " + model.milis);

//        System.out.println("Real PPQ: " + PPQ);
        System.out.println("");
        System.out.println("");

    }

     public static void echoInfoAboutSequence(Sequence seq) {
        float timingType =seq.getDivisionType();

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

            
            System.out.println("toString " + seq.toString());
            System.out.println("getDivisionType " + seq.getDivisionType());
            System.out.println("getMicrosecondLength " + seq.getMicrosecondLength());
            System.out.println("getPatchList " + seq.getPatchList());
            System.out.println("getResolution " + seq.getResolution());
            System.out.println("getTickLength " + seq.getTickLength());
//        System.out.println("numerator: " + seq. .numerator);
//        System.out.println("denominator: " + model.denominator);
//        System.out.println("ticksPerMetronomClixk: " + model.ticksPerMetronomClixk);
//
//        System.out.println("sequence.getTickLength()  " + model.sequenceOriginal.getTickLength());
//        System.out.println("sequence.getMicrosecondLength()  " + model.sequenceOriginal.getMicrosecondLength());
//        System.out.println("length in second: " + (double) model.sequenceOriginal.getMicrosecondLength() / 1000000);
//        System.out.println("sequence.getResolution(): " + model.PPQ);
//        System.out.println("minimal Note Length in ms: " + model.minimalNoteMsLength);
//        System.out.println("tempo: " + model.tempo);
//
//        System.out.println("milis: " + model.milis);

//        System.out.println("Real PPQ: " + PPQ);
        System.out.println("");
        System.out.println("");

    }

}
