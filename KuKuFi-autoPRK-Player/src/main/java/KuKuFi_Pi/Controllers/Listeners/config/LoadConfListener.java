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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoadConfListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        Gson gson = new Gson();

        String nameOfFile = Model.midiFile.getAbsolutePath() + ".json";

        DrumTrackPanelBase drumKit = MainWindow_Pi.window_Pi.getConnectPanel().getDrumKitElementPanel();
        DrumTrackPanelBase drumTrack = MainWindow_Pi.window_Pi.getConnectPanel().getDrumTrackPanel();

        try (InputStream in = new FileInputStream(nameOfFile)) {
            String json = IOUtils.toString(in, StandardCharsets.UTF_8);
            ConnectConfiguration readConf = gson.fromJson(json, ConnectConfiguration.class);

            for (ConfPair pair : readConf) {
                drumKit.moveRadioToUpOnPanel(drumKit.findByName(pair.getDrumKitElement()));
                drumTrack.moveRadioToUpOnPanel(drumTrack.findByName(pair.getDrumPart().getPartName()));
            }

            Model.connectConfig = readConf;

            log.info(json);
            MainWindow_Pi.window_Pi.getStep3Panel().repaint();
            MainWindow_Pi.window_Pi.pack();
        } catch (IOException e) {
            log.warn("cannot read configuration", e);
        }
    }
}
