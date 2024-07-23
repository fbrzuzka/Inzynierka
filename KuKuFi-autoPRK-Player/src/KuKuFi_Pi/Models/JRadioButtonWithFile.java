/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Models;

import java.io.File;
import javax.swing.JRadioButton;

/**
 *
 * @author fbrzuzka
 */
public class JRadioButtonWithFile extends JRadioButton{

    public JRadioButtonWithFile(File file) {
        super(file.getName());
        this.file = file;
    }
    
    
    private File file;

    public File getFile() {
        return file;
    }
    
    
    
}
