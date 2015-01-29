package AutoPRK.Models;

import AutoPRK.Models.Containers.TrackMap;
import AutoPRK.Models.Containers.ProtocolListHashMap;
import AutoPRK.Models.Containers.DrumPartList;
import AutoPRK.MessageController.SerialPortTransmiter;
import AutoPRK.MidiPlayer.*;
import AutoPRK.Models.Containers.ConnectConfiguration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbrzuzka
 */
public class Model {

    private static Model instance = null;

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public static int limitOfTrackLength = 50;
    public static SerialPortTransmiter serialPortTransmiter = null;

    public File midiFile;
    public double milis;
    public static Sequence sequenceToPlayFromSpeakers;
    public static Sequence sequenceOriginal;
    public static Track trackSelectedToGenerate;
    public static DrumPartList drumTrackElements;
    public static List<String> drumKitPartElements;
    public static ProtocolListHashMap protocolList = null;
    public static ConnectConfiguration connectConfig;

    public static TrackMap trackListToPlayFromSpeakers;
    public static SequencePlayer sequencePlayer;

    public int numerator = 0;
    public double denominator = 0;
    public int ticksPerMetronomClixk = 0;
    public int PPQ = 0;
    public double BPM = 0;
    public double tempo = 0;
    public Double minimalNoteMsLength = 1223412314234234.0;

    public static TrackMap trackListOriginal;

    private Model() {
        trackListOriginal = new TrackMap();
        trackListToPlayFromSpeakers = new TrackMap();
        serialPortTransmiter = new SerialPortTransmiter();
        connectConfig = new ConnectConfiguration();
        
        drumKitPartElements = new ArrayList<>();    
        drumKitPartElements.add("1");
        drumKitPartElements.add("2");
        drumKitPartElements.add("3");
        drumKitPartElements.add("4");
        drumKitPartElements.add("5");
        drumKitPartElements.add("6");
        drumKitPartElements.add("7");
        drumKitPartElements.add("8");
        drumKitPartElements.add("9");   
        drumKitPartElements.add("10"); 
    }

    public static Model instanceOf() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

}
