/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.MessageController;

import AutoPRK.Models.Message.DataMessage;
import AutoPRK.Models.Message.Message;
import AutoPRK.Models.Containers.ConfPair;
import AutoPRK.Models.DrumPart;
import AutoPRK.Models.Model;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author fbrzuzka
 */
public class PacketParser {

    private PacketParser() {

    }

    public static void parseString(String str) {

        byte bytePacket[] = str.getBytes();

       // System.out.println("typ. " + bytePacket[0]);
        //  System.out.println("2. " + bytePacket[1]);
        byte type = bytePacket[0];

        System.out.println("typ pakietu: " + type + " : " + MapOfPacketTypes.getType(type));

        switch (type) {
            case 101: {
                byte control = bytePacket[1];

                System.out.println(" : " + control + " : " + MapOfControlMessageTypes.getType(control));
                switch (control) {
                    case 111: {
                        break;
                    }
                    case 112: {
                        break;
                    }
                    case 113: {
                        break;
                    }
                    default: {

                        System.err.println(" nie znam takiego control: " + control);
                    }

                }

                break;
            }
            case 102: {
                byte nrPalki = bytePacket[1];
                System.out.println("nrPalki. " + bytePacket[1]);
                
                
                
                DrumPart drumPart = Model.connectConfig.getDrumPartByDrumKitElement(String.valueOf(nrPalki));
                
                
                String msTrackNoString = "";
                byte[] temp = new byte[1];
                
                if(bytePacket[2] >=48 && bytePacket[2] <=57){
                    temp[0] = bytePacket[2];
                    msTrackNoString += new String(temp);
                }
                if(bytePacket[3] >=48 && bytePacket[3] <=57){
                    temp[0] = bytePacket[3];
                    msTrackNoString += new String(temp);
                }
                if(bytePacket[4] >=48 && bytePacket[4] <=57){
                    temp[0] = bytePacket[4];
                    msTrackNoString += new String(temp);
                }
                if(bytePacket[5] >=48 && bytePacket[5] <=57){
                    temp[0] = bytePacket[5];
                    msTrackNoString += new String(temp);
                }
                
                           
                
                int msTrackNoInt = Integer.parseInt(msTrackNoString);

                int[] data = Model.protocolList.get(drumPart.getPartName()).get8IntsToArduino(msTrackNoInt);
                Model.serialPortTransmiter.sendData(new DataMessage((byte) 103, nrPalki, data));
              //  System.out.println("wysłałem i chyba sie udało :D");
                break;
            }
            default: {
                System.err.println(" nie znam takiego typu: " + type);

                break;
            }

        }

        //   return new Packet();
    }

}
