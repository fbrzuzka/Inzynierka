/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.MessageController;

import java.util.HashMap;

/**
 *
 * @author fbrzuzka
 */
public class MapOfPacketTypes extends HashMap<Byte, String> {

    private static MapOfPacketTypes typ = new MapOfPacketTypes();

    private MapOfPacketTypes() {
        super();
        put(new Byte("101"), "control   ");
        put(new Byte("102"), "askForData   ");
        put(new Byte("103"), "data   ");

    }
    
    public static String getType(Byte b){
        return typ.get(b);
    }

}
