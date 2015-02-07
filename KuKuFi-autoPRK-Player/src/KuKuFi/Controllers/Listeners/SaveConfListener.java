/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers.Listeners;

import KuKuFi.Models.Model;
import KuKuFi.views.MainWindow;
import com.google.gson.Gson;
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
        Gson gson = new Gson();
        String json = gson.toJson(Model.connectConfig);
        System.out.println(json);
        String nameOfFile = MainWindow.window.getMidiNameTextField().getText() + ".json";

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
