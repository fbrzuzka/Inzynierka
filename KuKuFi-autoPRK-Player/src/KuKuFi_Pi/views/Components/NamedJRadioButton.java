/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.views.Components;

import KuKuFi_Pi.Models.Model;
import java.awt.Font;
import javax.swing.JRadioButton;

/**
 *
 * @author fbrzuzka
 */
public class NamedJRadioButton extends JRadioButton{
    
    private String name;
    private int hits;
    
    public NamedJRadioButton(String text, int hits){
        super(text+ " (" + hits + ")");
        this.hits = hits;
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public int getHits() {
        return hits;
    }
    
}
