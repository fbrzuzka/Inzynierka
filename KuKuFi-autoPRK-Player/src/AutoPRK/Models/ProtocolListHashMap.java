package AutoPRK.Models;

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
public class ProtocolListHashMap extends LinkedHashMap<DrumPart, SingleDrumElementTimelineArray> {

    public ProtocolListHashMap() {
        super();

    }

    @Override
    public String toString() {
        String rc = "\n";

        for (DrumPart dr : this.keySet()) {
            rc += dr + " (" +this.get(dr).size() + ") : " +  this.get(dr).toString() + "\n";
        }
        return rc;
    }
//    public String toArduino(int limit) {
//        String rc = "\n";
//        int iter=0;
//        for (DrumPart dr : this.keySet()) {
//            rc +=  "unsigned char foo" + iter  + "[ODEBRANE] = " + this.get(dr).drToArduino(limit)+ "; \n";
//            iter++;
//        }
//        return rc;
//    }

}
