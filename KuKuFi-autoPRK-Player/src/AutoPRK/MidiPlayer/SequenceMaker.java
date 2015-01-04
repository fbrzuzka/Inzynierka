///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package AutoPRK.MidiPlayer;
//
//import AutoPRK.Controllers.ModelCreator;
//import AutoPRK.Models.TrackMap;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.sound.midi.InvalidMidiDataException;
//import javax.sound.midi.MidiEvent;
//import javax.sound.midi.Sequence;
//import javax.sound.midi.Track;
//
///**
// *
// * @author fbrzuzka
// */
//public class SequenceMaker {
//    
//    
//    public static Sequence makeSequence(Sequence seq, TrackMap mapOftrack){
//        
//        Sequence rc = null;
//                
//        try {
//             rc = new Sequence(seq.getDivisionType(), seq.getResolution(), mapOftrack.size());
//        } catch (InvalidMidiDataException ex) {
//            Logger.getLogger(SequenceMaker.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        Track[] tracks = rc.getTracks();
//        
//        if(mapOftrack.size() != tracks.length){
//            System.out.println("looool, coś poszło nie tak i dlugość ścieżek i mapy jest inna;/");
//            
//        }
//        
//        int index = 0;
//        for (Map.Entry<String, Track> entrySet : mapOftrack.entrySet()) {
//            
//           Track value = entrySet.getValue();
//           
//            for(int i=0 ; i < value.size() ; i++) {
//                MidiEvent me = value.get(i);
//                rc.getTracks()[index].add(me);
//            }
//           index++;
//        }               
//        ModelCreator.echoInfoAboutSequence(seq);
//        ModelCreator.echoInfoAboutSequence(rc);
//        
//        return rc;
//    }
//   
//    
//}
