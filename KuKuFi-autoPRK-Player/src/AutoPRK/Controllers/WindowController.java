/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers;

import AutoPRK.Controllers.Listeners.GenerateListener;
import AutoPRK.Controllers.Listeners.OpenFileChooserListener;
import AutoPRK.views.mainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author fbrzuzka
 */
public class WindowController {
    mainWindow window;

    public WindowController(mainWindow window) {
        this.window = window;
        configureListeners();
    }
    private void configureListeners(){
        window.getGenerateButton().addActionListener(new GenerateListener());
        window.getOpenButton().addActionListener(new OpenFileChooserListener());
    }
    
    
    
}
