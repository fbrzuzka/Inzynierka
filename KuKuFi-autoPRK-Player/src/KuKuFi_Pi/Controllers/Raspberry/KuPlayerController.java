/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Raspberry;

import KuKuFi_Pi.Models.Model;

/**
 *
 * @author fbrzuzka
 */
public class KuPlayerController {
    
    public Thread kuPlayerThread;

    public KuPlayerController() {
        this.kuPlayerThread = new Thread(new KuPlayer());
    }
    
     public void start() {
        kuPlayerThread.start();
        
    }
     
     public void pause() {
        kuPlayerThread.interrupt();
        
    }
    
     public void stop() {
         
        kuPlayerThread.interrupt();
        this.kuPlayerThread = new Thread(new KuPlayer());
        
     }
    
    
    
}
