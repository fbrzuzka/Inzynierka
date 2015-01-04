package AutoPRK.Models;


import AutoPRK.MidiPlayer.*;
import java.io.File;
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
    
    public File midiFile;
    public double milis;
    public static Sequence sequenceToPlayFromSpeakers;
    public static Sequence sequenceOriginal;
    public static Track trackSelectedToGenerate;
    public static ProtocolListHashMap protocolList = null;
    
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
    
    private Model(){
       trackListOriginal = new TrackMap();
       trackListToPlayFromSpeakers = new TrackMap();
    }
    
    public static Model instanceOf(){
        if(instance == null){
            instance = new Model();
        }        
        return instance;
    }
    
}
