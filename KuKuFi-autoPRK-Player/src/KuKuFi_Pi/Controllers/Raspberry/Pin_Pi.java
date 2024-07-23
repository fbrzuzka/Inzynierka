/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Raspberry;

import KuKuFi_Pi.Models.Model;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 *
 * @author fbrzuzka
 */
public class Pin_Pi {
    
    String name;

    public Pin_Pi(String number) {
        this.name = "pin" + (Integer.parseInt(number) -1);
        System.out.println("utworzylem: " + name);
    }
    
    public void hit(){
        
    }
    
    void low(){
        Model.outputPinMap.get(name).low();
      //  System.out.println("pin Low");
    }
    
    void high(){
        Model.outputPinMap.get(name).high();
     //   System.out.println("pin High");
    }
    
}
