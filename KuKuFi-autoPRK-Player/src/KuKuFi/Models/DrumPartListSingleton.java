package AutoPRK.Models;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbrzuzka
 */
public class DrumPartListSingleton extends LinkedHashMap<Integer, DrumPart> {

    private static DrumPartListSingleton instance = null;

    private DrumPartListSingleton() {
        super();
        put(35, new DrumPart(35, "Acoustic Bass Drum"));
        put(36, new DrumPart(36, "Bass Drum 1"));
        put(37, new DrumPart(37, "Side Stick"));
        put(38, new DrumPart(38, "Acoustic Snare"));
        put(39, new DrumPart(39, "Hand Clap"));
        put(40, new DrumPart(40, "Electric Snare"));
        put(41, new DrumPart(41, "Low Floor Tom"));
        put(42, new DrumPart(42, "Closed Hi-Hat"));
        put(43, new DrumPart(43, "High Floor Tom"));
        put(44, new DrumPart(44, "Pedal Hi-Hat"));
        put(45, new DrumPart(45, "Low Tom"));
        put(46, new DrumPart(46, "Open Hi-Hat"));
        put(47, new DrumPart(47, "Low-Mid Tom"));
        put(48, new DrumPart(48, "Hi-Mid Tom"));
        put(49, new DrumPart(49, "Crash Cymbal 1"));
        put(50, new DrumPart(50, "High Tom"));
        put(51, new DrumPart(51, "Ride Cymbal 1"));
        put(52, new DrumPart(52, "Chinese Cymbal"));
        put(53, new DrumPart(53, "Ride Bell"));
        put(54, new DrumPart(54, "Tambourine"));
        put(55, new DrumPart(55, "Splash Cymbal"));
        put(56, new DrumPart(56, "Cowbell"));
        put(57, new DrumPart(57, "Crash Cymbal 2"));
        put(58, new DrumPart(58, "Vibraslap"));
        put(59, new DrumPart(59, "Ride Cymbal 2"));
        put(60, new DrumPart(60, "Hi Bongo"));
        put(61, new DrumPart(61, "Low Bongo"));
        put(62, new DrumPart(62, "Mute Hi Conga"));
        put(63, new DrumPart(63, "Open Hi Conga"));
        put(64, new DrumPart(64, "Low Conga"));
        put(65, new DrumPart(65, "High Timbale"));
        put(66, new DrumPart(66, "Low Timbale"));
        put(67, new DrumPart(67, "High Agogo"));
        put(68, new DrumPart(68, "Low Agogo"));
        put(69, new DrumPart(69, "Cabasa"));
        put(70, new DrumPart(70, "Maracas"));
        put(71, new DrumPart(71, "Short Whistle"));
        put(72, new DrumPart(72, "Long Whistle"));
        put(73, new DrumPart(73, "Short Guiro"));
        put(74, new DrumPart(74, "Long Guiro"));
        put(75, new DrumPart(75, "Claves"));
        put(76, new DrumPart(76, "Hi Wood Block"));
        put(77, new DrumPart(77, "Low Wood Block"));
        put(78, new DrumPart(78, "Mute Cuica"));
        put(79, new DrumPart(79, "Open Cuica"));
        put(80, new DrumPart(80, "Mute Triangle"));
        put(81, new DrumPart(81, "Open Triangle"));
 
 
 /*
    put("A2", new DrumPart("A2"));
    put("A3", new DrumPart("A3"));
    put("A4", new DrumPart("A4"));
    
    put("A#2", new DrumPart("A#2"));
    
    put("B1", new DrumPart("B1"));
    put("B2", new DrumPart("B2"));
    put("B3", new DrumPart("B3"));
    
    put("C2", new DrumPart("C2"));
    put("C3", new DrumPart("C3"));
    
    put("C#2", new DrumPart("C#2"));
    put("C#3", new DrumPart("C#3"));
    
    put("D2", new DrumPart("D2"));
    put("D3", new DrumPart("D3"));
    put("D4", new DrumPart("D4"));
    
    put("D#3", new DrumPart("D#3"));
    put("D#5", new DrumPart("D#5"));
    
    put("E2", new DrumPart("E2"));
    
    put("F2", new DrumPart("F2"));
    
    put("F#2", new DrumPart("F#2"));
    
    put("G2", new DrumPart("G2"));
    put("G3", new DrumPart("G3"));
    
    put("G#2", new DrumPart("G#2"));
         */
    }
    public static DrumPartListSingleton getInstance() {
        if (instance == null) {
            instance = new DrumPartListSingleton();
        }
        return instance;
    }

}
