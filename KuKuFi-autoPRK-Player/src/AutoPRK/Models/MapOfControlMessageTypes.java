/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Models;

import java.util.HashMap;

/**
 *
 * @author fbrzuzka
 */
public class MapOfControlMessageTypes extends HashMap<Byte, String> {

    private static MapOfControlMessageTypes typ = new MapOfControlMessageTypes();

    private MapOfControlMessageTypes() {
        super();
        put(new Byte("111"), "play");
        put(new Byte("112"), "pause");
        put(new Byte("113"), "stop");

    }
    
    public static String getType(Byte b){
        return typ.get(b);
    }

}
