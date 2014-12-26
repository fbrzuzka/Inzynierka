package AutoPRK.Models;



import java.io.File;
import javax.sound.midi.Sequence;

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
    
    public File midiFile;
    public double milis;
    public Sequence sequence;
    public ProtocolListHashMap protocolList = null;
    
    public int numerator = 0;
    public double denominator = 0;
    public int ticksPerMetronomClixk = 0;
    public int PPQ = 0;
    public double BPM = 0;
    public double tempo = 0;
    public Double minimalNoteMsLength = 1223412314234234.0;
    
    private Model(){
       
    }
    
    public static Model instanceOf(){
        if(instance == null){
            instance = new Model();
        }        
        return instance;
    }
    
}
