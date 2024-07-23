/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners.config;

import KuKuFi_Pi.Models.Containers.ConfPair;
import KuKuFi_Pi.Models.Containers.ConnectConfiguration;
import KuKuFi_Pi.Models.Model;
import KuKuFi_Pi.views.Components.DrumTrackPanelBase;
import KuKuFi_Pi.views.MainWindow_Pi;
import com.google.gson.Gson;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        String nameOfFile = Model.midiFile.getAbsolutePath() + ".json";

        DrumTrackPanelBase drumKit = MainWindow_Pi.window_Pi.getConnectPanel().getDrumKitElementPanel();
        DrumTrackPanelBase drumTrack = MainWindow_Pi.window_Pi.getConnectPanel().getDrumTrackPanel();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(nameOfFile));
        } catch (FileNotFoundException ex) {
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
        } catch (NullPointerException npe){}

        System.out.println(json);
        MainWindow_Pi.window_Pi.getStep3Panel().repaint();
        MainWindow_Pi.window_Pi.pack();

    }
}
