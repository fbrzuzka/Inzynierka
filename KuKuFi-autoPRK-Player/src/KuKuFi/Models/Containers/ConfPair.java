/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Models.Containers;

import KuKuFi.Models.DrumPart;


/**
 *
 * @author fbrzuzka
 */
public class ConfPair{

    private String drumKitElement;
    private DrumPart drumPart;

    public ConfPair(String drumKitElement, DrumPart drumPart) {
        this.drumKitElement = drumKitElement;
        this.drumPart = drumPart;
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
    
    
}
