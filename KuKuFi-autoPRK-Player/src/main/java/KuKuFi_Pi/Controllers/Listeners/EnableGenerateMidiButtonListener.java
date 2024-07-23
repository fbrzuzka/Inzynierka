/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners;

import KuKuFi_Pi.views.MainWindow_Pi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fbrzuzka
 */
public class EnableGenerateMidiButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindow_Pi.window_Pi.getGenerateButton().setEnabled(true);
    }
    
}
