package AutoPRK.Models.Containers;

import AutoPRK.Models.DrumPart;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbrzuzka
 */
public class SingleDrumElementTimelineArray extends ArrayList<Integer> {

    private DrumPart drumPart;

    public SingleDrumElementTimelineArray(DrumPart drumPart) {
        super();
        this.drumPart = drumPart;
    }

    /**
     * @return the drumPart
     */
    public DrumPart getDrumPart() {
        return drumPart;
    }

    public int[] get8IntsToArduino(int index) {
        int[] rc = new int[8];
        for (int i = 0; i < 8; i++) {
            if (index + i >= this.size()) {
                rc[i] = 0;
            } else {
                rc[i] = this.get(index + i);
            }
        }

        return rc;
    }

}
//      public String drToArduino(int limit){
//        String rc = "{ " + this.get(0) + ",\t";
//        for(int i=1 ; i < this.size() - 1 ; i++) {
//            if(i == limit){
//                break;
//            }
//              rc += this.get(i) + ",\t";
//          }
//          rc += "}";
//        return rc;
//    }
//
//public class SingleDrumElementTimelineArray extends ArrayList<ByteNote>{
//    private DrumPart drumPart;
//    public SingleDrumElementTimelineArray(DrumPart drumPart){
//        super();
//        this.drumPart = drumPart;
//    }
//
//    /**
//     * @return the drumPart
//     */
//    public DrumPart getDrumPart() {
//        return drumPart;
//    }
//      public String drToArduino(int limit){
//        String rc = "{ " + this.get(0) + ",\t";
//        for(int i=1 ; i < this.size() - 1 ; i++) {
//            if(i == limit){
//                break;
//            }
//              rc += this.get(i) + ",\t";
//          }
//          rc += "}";
//        return rc;
//    }
//    
//}
