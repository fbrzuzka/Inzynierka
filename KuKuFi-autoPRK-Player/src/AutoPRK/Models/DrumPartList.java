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
public class DrumPartList extends LinkedHashMap<String, DrumPart>{
    
    private static DrumPartList instance = null;
    
    private DrumPartList(){
    super();
    
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
    }
    public static DrumPartList getInstance() {
        if(instance == null){
            instance = new DrumPartList();
        }
        return instance;
    }
    
  
     
   
}
