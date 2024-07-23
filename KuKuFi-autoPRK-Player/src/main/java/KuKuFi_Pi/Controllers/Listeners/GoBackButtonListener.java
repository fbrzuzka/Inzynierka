/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners;

import KuKuFi_Pi.Controllers.WindowController_Pi;
import KuKuFi_Pi.Models.Containers.ConnectConfiguration;
import KuKuFi_Pi.Models.Model;
import KuKuFi_Pi.views.MainWindow_Pi;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fbrzuzka
 */
public class GoBackButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        
        WindowController_Pi.getInstance().removeConnectRadioButtons();
        Model.connectConfig = new ConnectConfiguration();
        
        CardLayout cl = (CardLayout)(MainWindow_Pi.window_Pi.getMainPanel().getLayout());
        cl.show(MainWindow_Pi.window_Pi.getMainPanel(), "card1");
        //otwórz nową stronę
    }
    
}
