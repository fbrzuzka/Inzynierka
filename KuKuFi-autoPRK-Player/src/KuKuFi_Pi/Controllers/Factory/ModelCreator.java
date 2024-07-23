package KuKuFi_Pi.Controllers.Factory;

import KuKuFi_Pi.Models.Containers.DrumPartList;
import KuKuFi_Pi.Models.Containers.ProtocolListHashMap;
import KuKuFi_Pi.Models.Containers.SingleDrumElementTimelineArray;
import KuKuFi_Pi.Models.DrumPart;
import KuKuFi_Pi.Models.DrumPartListSingleton;
import KuKuFi_Pi.Models.Model;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import static javax.sound.midi.ShortMessage.NOTE_ON;
import javax.sound.midi.Track;

public class ModelCreator {

    static Model model = Model.instanceOf();
    private static final Integer leadTimeMilis = 20;
    private static final int numberOfDrumKitFromBiedrzyProgram = 10;

    public ModelCreator() {
        Model.protocolList = new ProtocolListHashMap();
        setResolutionInMs(Model.sequenceOriginal);
        echoInfoAboutModel();
        Model.protocolList = newParseToHashMap(model.trackSelectedToGenerate);

    }

    public DrumPartList addElementsToDrumTrack() {
        DrumPartList dpl = new DrumPartList();
        return dpl;
    }

    private ProtocolListHashMap newParseToHashMap(Track track) {
        ProtocolListHashMap rc = new ProtocolListHashMap();

        Model.drumTrackElements = findElementOfDrumKit(track);

        for (DrumPart drumKitElement : Model.drumTrackElements) {
            rc.put(drumKitElement.getPartName(), new SingleDrumElementTimelineArray(drumKitElement));
        }

        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            int tempoTickInMs = (int) ((event.getTick() * model.tickToMilisRatio * 1000) / 1000);
            MidiMessage message = event.getMessage();
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                if (sm.getCommand() == NOTE_ON) {

                    int key = sm.getData1();
                    int velocity = sm.getData2();

//                    System.out.print("@" + event.getTick() + " " + tempoTickInMs + " ");
//                    System.out.print("Channel: " + sm.getChannel() + " ");
//                    System.out.println(" velocity: " + velocity + DrumPartListSingleton.getInstance().get(key).toString());
                    DrumPart drumPart = DrumPartListSingleton.getInstance().get(key);
                    rc.get(drumPart.getPartName()).add(tempoTickInMs);

                }
            }
        }
        System.out.println();
        System.out.println("rc: " + rc.toString());
        rc = splitClosedHiHatToOpenAndPedal(rc);
        System.out.println();
        System.out.println("splitClosedHiHatToOpenAndPedal rc: " + rc.toString());

        fillToTenDrumElements(Model.drumTrackElements, rc);

        return rc;

    }

    private void fillToTenDrumElements(DrumPartList drumTrackElements, ProtocolListHashMap protocolListHashMap) {

        ArrayList<DrumPart> drumPartToAdd = new ArrayList<DrumPart>();

        if (drumTrackElements.size() < numberOfDrumKitFromBiedrzyProgram) {
            int numberOfMissed = numberOfDrumKitFromBiedrzyProgram - drumTrackElements.size();
            for (int i = 1; i <= numberOfMissed; i++) {
                drumTrackElements.add(DrumPartListSingleton.getInstance().get(100 + i));
                drumPartToAdd.add(DrumPartListSingleton.getInstance().get(100 + i));
            }
        }

        if (protocolListHashMap.size() < numberOfDrumKitFromBiedrzyProgram) {
            int numberOfMissed = numberOfDrumKitFromBiedrzyProgram - protocolListHashMap.size();
            int milisToOdd = 40;
            int i = 0;
            for (DrumPart drumPart : drumPartToAdd) {
                SingleDrumElementTimelineArray singleDrumElementTimelineArray = new SingleDrumElementTimelineArray(drumPart);
                singleDrumElementTimelineArray.add((int) Model.sequenceOriginal.getTickLength() - milisToOdd * i);
                protocolListHashMap.put(drumPart.getPartName(), singleDrumElementTimelineArray);
            }
        }
        for (DrumPart drumPart : drumTrackElements) {
            System.out.println(drumPart);
        }

    }

    public ProtocolListHashMap splitClosedHiHatToOpenAndPedal(ProtocolListHashMap protocolListHashMap) {

        SingleDrumElementTimelineArray closedHiHat;
        SingleDrumElementTimelineArray openHiHat;
        SingleDrumElementTimelineArray pedalHiHat;

        if (protocolListHashMap.containsKey("Closed Hi-Hat")) {
            //get closed HiHat SingleDrumElementTimelineArray and remove it from protocolArrayList
            closedHiHat = protocolListHashMap.remove("Closed Hi-Hat");
            Model.drumTrackElements.remove(DrumPartListSingleton.getInstance().get(42));
        } else {
            return protocolListHashMap;
        }

        if (protocolListHashMap.containsKey("Open Hi-Hat")) {
            //get open HiHat SingleDrumElementTimelineArray
            openHiHat = protocolListHashMap.get("Open Hi-Hat");
        } else {
            DrumPart openHiHatDrumPart = DrumPartListSingleton.getInstance().get(46);
            openHiHat = new SingleDrumElementTimelineArray(openHiHatDrumPart);
            protocolListHashMap.put("Open Hi-Hat", openHiHat);
            Model.drumTrackElements.add(openHiHatDrumPart);
        }
        if (protocolListHashMap.containsKey("Pedal Hi-Hat")) {
            //get pedal HiHat SingleDrumElementTimelineArray
            pedalHiHat = protocolListHashMap.get("Pedal Hi-Hat");
        } else {
            DrumPart pedalHiHatDrumPart = DrumPartListSingleton.getInstance().get(44);
            pedalHiHat = new SingleDrumElementTimelineArray(pedalHiHatDrumPart);
            protocolListHashMap.put("Pedal Hi-Hat", pedalHiHat);
            Model.drumTrackElements.add(pedalHiHatDrumPart);
        }

        for (Integer time : closedHiHat) {
            openHiHat.add(time);
            if (time >= leadTimeMilis) {
                pedalHiHat.add(time - leadTimeMilis);
            }
        }

        //need some sort
        Collections.sort(openHiHat);
        Collections.sort(pedalHiHat);

        return protocolListHashMap;
    }

    public static Sequence getSequenceFromMidiFile(File file) {

        Sequence rc = null;
        try {
            rc = MidiSystem.getSequence(file);
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(ModelCreator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
        model.tickToMilisRatio = model.sequenceOriginal.getMicrosecondLength() / (model.sequenceOriginal.getTickLength() * 1000.0);
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
            model.denominator = pow(2, data[1]);
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

        System.out.println("milis: " + model.tickToMilisRatio);
        System.out.println("");
        System.out.println("");

    }

}
