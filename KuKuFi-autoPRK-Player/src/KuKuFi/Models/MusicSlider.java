/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Models;

import KuKuFi.MidiPlayer.MusicSliderListener;
import javax.swing.JSlider;

/**
 *
 * @author fbrzuzka
 */
public class MusicSlider extends JSlider implements MusicSliderListener{

    @Override
    public void handleEvent(int position) {
        this.setValue(position);
    }
    
}
