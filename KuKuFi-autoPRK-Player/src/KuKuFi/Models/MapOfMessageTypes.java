/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Models;

import java.util.HashMap;

/**
 *
 * @author fbrzuzka
 */
public class MapOfMessageTypes extends HashMap<Byte, String> {

    private static MapOfMessageTypes typ = new MapOfMessageTypes();

    private MapOfMessageTypes() {
        super();
        put(new Byte("101"), "control   ");
        put(new Byte("102"), "askForData   ");
        put(new Byte("103"), "data   ");

    }
    
    public static String getType(Byte b){
        return typ.get(b);
    }

}
