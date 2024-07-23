/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Raspberry;

import KuKuFi_Pi.Models.Containers.ProtocolListHashMap;
import KuKuFi_Pi.Models.Containers.SingleDrumElementTimelineArray;
import KuKuFi_Pi.Models.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fbrzuzka
 */
public class KuPlayer implements Runnable{
    
    private PiExecutor piExecutor = new PiExecutor(6);
    private ProtocolListHashMap listToPlay = new ProtocolListHashMap();
    
    
    @Override
    public void run() {
        listToPlay = prepareListToPlay( Model.protocolList);
        Long beginTime = System.currentTimeMillis();
        
        int[] tab = new int[10];
        
        List keys = new ArrayList(listToPlay.keySet());
        
        // aktualnie rozpatrywany bęben i jego milisekundy
        SingleDrumElementTimelineArray array;
        
        while (!Thread.currentThread().isInterrupted()) {
         
            Long now = System.currentTimeMillis();
            for (int i = 0; i < keys.size(); i++) {
                try{
                    array = listToPlay.get(keys.get(i));
                    int iii = tab[i];
                    Integer lo = array.get(iii);
                    if(lo < now - beginTime){
                        piExecutor.exec(array.getDrumPart());
                        System.out.println("graj " + keys.get(i) + "!");
                        System.out.println("");
                        tab[i]++;
                        sleep(5);
                    }
                    
                }catch(IndexOutOfBoundsException iobe) {
                    System.out.println("index of bound: " + keys.get(i)); 
                    keys.remove(i);
                    if(keys.isEmpty()){
                        break;
                    }
                }
                    
            }
         }
        
        
    }

    private void sleep(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Logger.getLogger(KuPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ProtocolListHashMap prepareListToPlay(ProtocolListHashMap protocolListHashMap) {
        for(int i=0 ; i<Model.connectConfig.size() ; i++){
            String drumKitElement = Model.connectConfig.get(i).getDrumPart().getPartName();
            listToPlay.put(drumKitElement, protocolListHashMap.get(drumKitElement));
        }
        System.out.println("list to play : \n" + listToPlay.toString());
        return listToPlay;
    }

    
 

    
    
}
