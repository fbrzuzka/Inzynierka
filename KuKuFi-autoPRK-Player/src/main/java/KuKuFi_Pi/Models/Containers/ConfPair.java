/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Models.Containers;

import KuKuFi_Pi.Controllers.Raspberry.Pin_Pi;
import KuKuFi_Pi.Models.DrumPart;
import com.pi4j.io.gpio.GpioPinDigitalOutput;


/**
 *
 * @author fbrzuzka
 */
public class ConfPair{

    private String drumKitElement;
    private DrumPart drumPart;
    private Pin_Pi pin;

   
    public ConfPair(String drumKitElement, DrumPart drumPart, Pin_Pi pin) {
        this.drumKitElement = drumKitElement;
        this.drumPart = drumPart;
        this.pin = pin;
    }
    
    
    @Override
    public String toString(){
        return "You choose a pair: " + drumKitElement + " -- " + drumPart.getPartName();
        
    }

    
    public String getDrumKitElement() {
        return drumKitElement;
    }

    public DrumPart getDrumPart() {
        return drumPart;
    }

    public Pin_Pi getPin() {
        return pin;
    }

    public void setPin(Pin_Pi pin) {
        this.pin = pin;
    }
    
    
    
}
