/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers.Listeners;

import AutoPRK.Models.Containers.ConfPair;
import AutoPRK.Models.Containers.ConnectConfiguration;
import AutoPRK.Models.Model;
import AutoPRK.views.Components.DrumTrackPanelBase;
import AutoPRK.views.mainWindow;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fbrzuzka
 */
public class LoadConfListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        Gson gson = new Gson();
        String json = "";
        String nameOfFile = mainWindow.window.getMidiNameTextField().getText() + ".json";

        DrumTrackPanelBase drumKit = mainWindow.window.getConnectPanel().getDrumKitElementPanel();
        DrumTrackPanelBase drumTrack = mainWindow.window.getConnectPanel().getDrumTrackPanel();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nameOfFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadConfListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            json = reader.readLine();
            ConnectConfiguration readedConf = gson.fromJson(json, ConnectConfiguration.class);

            for (ConfPair pair : readedConf) {
                drumKit.moveRadioToUpOnPanel(drumKit.findByName(pair.getDrumKitElement()));
                drumTrack.moveRadioToUpOnPanel(drumTrack.findByName(pair.getDrumPart().getPartName()));
            }

            Model.connectConfig = readedConf;

        } catch (IOException ex) {
            Logger.getLogger(LoadConfListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(json);
        mainWindow.window.getStep3Panel().repaint();
        mainWindow.window.pack();

    }
}
