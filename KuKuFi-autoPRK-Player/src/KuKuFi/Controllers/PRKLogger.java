/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Controllers;

import KuKuFi.views.MainWindow;

/**
 *
 * @author fbrzuzka
 */
public class PRKLogger {
    
    private MainWindow window;
    private PRKLogger() {
        this.window = MainWindow.window;
    }
    
    public static PRKLogger instance() {
        return LoggerHolder.INSTANCE;
    }
    
    private static class LoggerHolder {

        private static final PRKLogger INSTANCE = new PRKLogger();
        
        
    }
    
    public void logToInfoArea(String str){
        window.getInfoArea().append(str + "\n");
    }

    
}
