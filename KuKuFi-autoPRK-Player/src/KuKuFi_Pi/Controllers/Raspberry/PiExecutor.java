/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Raspberry;

import KuKuFi_Pi.Models.DrumPart;
import KuKuFi_Pi.Models.Model;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author fbrzuzka
 */
public class PiExecutor {

    private static Executor executor;
    
    public PiExecutor(int threadsCount) {
        executor = Executors.newCachedThreadPool();
        
    }
    
    public void exec(DrumPart drumPart){
        Pin_Pi pin = Model.connectConfig.getPinByDrumPart(drumPart);
        executor.execute(new Task(pin));
        
    }
  
}