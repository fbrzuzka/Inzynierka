/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Listeners.config;

import KuKuFi_Pi.Models.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author fbrzuzka
 */
public class SaveConfListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent ae) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(Model.connectConfig);
        System.out.println(json);
        String nameOfFile = Model.midiFile.getAbsolutePath() + ".json";

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(nameOfFile));
            writer.write(json);
        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
