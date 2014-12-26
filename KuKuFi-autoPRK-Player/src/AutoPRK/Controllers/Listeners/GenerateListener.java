/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Controllers.ModelCreator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;

/**
 *
 * @author fbrzuzka
 */
public class GenerateListener implements ActionListener{

    public GenerateListener() {
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        ModelCreator modelCreator = new ModelCreator();
    }
    
}
