package AutoPRK.Controllers;

import AutoPRK.Models.ByteNote;
import AutoPRK.Models.DrumPart;
import AutoPRK.Models.DrumPartList;
import com.sun.org.apache.xalan.internal.lib.ExsltMath;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import AutoPRK.Models.ProtocolListHashMap;
import AutoPRK.Models.Model;
import static AutoPRK.Models.Model.NOTE_NAMES;
import AutoPRK.Models.SingleDrumElementTimelineArray;
import java.util.ArrayList;
import java.util.List;
import static javax.sound.midi.ShortMessage.NOTE_ON;

public class ModelCreator {

    Model model = Model.instanceOf();

    public ModelCreator() {
        model.protocolList = new ProtocolListHashMap();
        model.sequence = getSequenceFromMidiFile(model.midiFile);
        setSDontRememberWhat(model.sequence);
        echoInfoAboutModel(model.sequence);
        parseSequenceToArrayList(model.sequence);

    }

    private File getMidiFileFromDisk(String name) {

        File rc = null;
        try {
            rc = new File(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rc;
    }

    private Sequence getSequenceFromMidiFile(File file) {

        Sequence rc = null;
        try {
            rc = MidiSystem.getSequence(file);
        } catch (InvalidMidiDataException | IOException ex) {
            Logger.getLogger(ModelCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rc;
    }

    private void echoInfoAboutModel(Sequence seq) {
        float timingType = model.sequence.getDivisionType();

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

        System.out.println("sequence.getTickLength()  " + model.sequence.getTickLength());
        System.out.println("sequence.getMicrosecondLength()  " + model.sequence.getMicrosecondLength());
        System.out.println("length in second: " + (double) model.sequence.getMicrosecondLength() / 1000000);
        System.out.println("sequence.getResolution(): " + model.PPQ);
        System.out.println("minimal Note Length in ms: " + model.minimalNoteMsLength);
        System.out.println("tempo: " + model.tempo);

       
              System.out.println("milis: " + model.milis);

//        System.out.println("Real PPQ: " + PPQ);
        System.out.println("");
        System.out.println("");
    }

    private void setSDontRememberWhat(Sequence seq) {
         model.milis = model.sequence.getMicrosecondLength() / (model.sequence.getTickLength() * 1000.0);
        model.PPQ = model.sequence.getResolution();
        
       
        for (Track track : seq.getTracks()) {

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
                    MetaMessage mm = (MetaMessage) message;
                    echoMetaMessageInfo(mm);

                }
            }
            model.minimalNoteMsLength = min;
            model.tempo = 125.0/min;
            System.out.println();

        }
    }

    private void initTimelineWithBlankElements(SingleDrumElementTimelineArray timeLine, long microSecLength) {
        int stop = (int) (Math.ceil(microSecLength * model.tempo / 1000000 ));
        for (int i = 0; i < stop; i++) {
            timeLine.add(new ByteNote(ByteNote.EMPTY_BYTE));
        }
    }

    private ProtocolListHashMap parseSequenceToArrayList(Sequence seq) {
        ProtocolListHashMap rc = null;

        int trackNumber = 0;
        for (Track track : seq.getTracks()) {
            rc = new ProtocolListHashMap();

            List<String> listOfDifferentNotes = findSingleNoteInTrack(track);

            for (String differentNote : listOfDifferentNotes) {
                DrumPart dr = DrumPartList.getInstance().get(differentNote);
                SingleDrumElementTimelineArray elementTimeLine = new SingleDrumElementTimelineArray(dr);
                initTimelineWithBlankElements(elementTimeLine, model.sequence.getMicrosecondLength());
                rc.put(dr, elementTimeLine);
            }

            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();

            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);

                double tickInMs = (event.getTick() * model.milis);
                double tempoTickInMs  = tickInMs * model.tempo;
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) {

                        System.out.print("@" + event.getTick() + " " + tempoTickInMs + " ");
                        System.out.print("Channel: " + sm.getChannel() + " ");

                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        String noteAndOctave = noteName + octave;

                        int indexOfNotetoSet = (int) (tempoTickInMs / 1000);

                        DrumPart drumPart;
                        drumPart = DrumPartList.getInstance().get(noteAndOctave);

                        ByteNote noteToSet = rc.get(drumPart).get(indexOfNotetoSet);
                        noteToSet.setByteNote(event);

                        rc.get(drumPart).set(indexOfNotetoSet, noteToSet);

                    }

                }
            }
            System.out.println();
        }
        System.out.println("rc: " + rc.toArduino(50));
        return rc;
    }

    private List<String> findSingleNoteInTrack(Track track) {

        List<String> l = new ArrayList();

        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);

            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                if (sm.getCommand() == NOTE_ON) {

                    int key = sm.getData1();
                    int octave = (key / 12) - 1;
                    int note = key % 12;
                    String noteName = NOTE_NAMES[note];
                    String noteAndOctave = noteName + octave;

                    if (!l.contains(noteAndOctave)) {
                        l.add(noteAndOctave);
                    }
                }

            }
        }
        System.out.println("lista: " + l);
        return l;
    }

    
        private void echoMetaMessageInfo(MetaMessage mm) {

        int length = mm.getLength();
        int status = mm.getStatus();
 //       System.out.print(" status: " + status);
 //       System.out.print("length: " + length);
 //       System.out.print(" typ: " + mm.getType() + " data" + Arrays.toString(mm.getData()));

        if (mm.getType() == 88) {
            byte[] data = mm.getData();
            model.numerator = data[0];
            model.denominator = ExsltMath.power(2, data[1]);
            model.ticksPerMetronomClixk = data[2];

            System.out.println("numerator: " + model.numerator);
            System.out.println("denominator: " + model.denominator);
            System.out.println("ticksPerMetronomClixk: " + model.ticksPerMetronomClixk);
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
        
}
