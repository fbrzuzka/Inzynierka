/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Controllers.Logger;
import AutoPRK.Models.Model;
import AutoPRK.views.mainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author fbrzuzka
 */
public class OpenFileChooserListener implements ActionListener {
    
    private Model model;
    private mainWindow window;

    public OpenFileChooserListener(){
        
        this.window = mainWindow.window;
        this.model = Model.instanceOf();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        JFileChooser c = new JFileChooser();
        // Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(mainWindow.window);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            window.getMidiNameTextField().setText(c.getSelectedFile().getName());
            window.getGenerateButton().setEnabled(true);
            model.midiFile = c.getSelectedFile();
          
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
             window.getMidiNameTextField().setText("Select file ->");
        }

    }

}
