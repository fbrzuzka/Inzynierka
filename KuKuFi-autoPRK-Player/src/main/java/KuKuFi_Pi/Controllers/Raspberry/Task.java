/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Raspberry;

import lombok.extern.slf4j.Slf4j;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
class Task implements Runnable {

    private final Pin_Pi pin;

    public Task(Pin_Pi pin) {
        this.pin = pin;
        log.info("teraz gra: {}", pin.name);
    }

    public void run() {
        pin.low();
        try {
            Thread.sleep(67);
        } catch (InterruptedException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
        pin.high();
        Thread.yield();
    }
}
