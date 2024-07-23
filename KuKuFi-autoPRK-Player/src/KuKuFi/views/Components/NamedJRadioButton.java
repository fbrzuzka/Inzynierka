/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.views.Components;

import javax.swing.JRadioButton;

/**
 *
 * @author fbrzuzka
 */
public class NamedJRadioButton extends JRadioButton{
    
    private String name;
    
    public NamedJRadioButton(String text){
        super(text);
        
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
    
}
