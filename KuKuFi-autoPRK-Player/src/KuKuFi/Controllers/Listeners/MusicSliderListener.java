/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers.Listeners;

import KuKuFi.Models.Model;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JSlider;

/**
 *
 * @author fbrzuzka
 */
public class MusicSliderListener implements MouseListener{

   

    @Override
    public void mousePressed(MouseEvent me) {
        JSlider slider = (JSlider) me.getSource();
        double x = slider.getMousePosition().getX();
        int max = slider.getMaximum();
        int width = slider.getSize().width;
        int newValue = (int)(x * max / width);
        
        slider.setValue(newValue);
        long tickPosition = (long)(x * Model.sequenceOriginal.getTickLength() / width);
        Model.sequencePlayer.setTickPosition(tickPosition);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        }

    @Override
    public void mouseExited(MouseEvent me) {
      }
    @Override
    public void mouseClicked(MouseEvent me) {
         }
}
