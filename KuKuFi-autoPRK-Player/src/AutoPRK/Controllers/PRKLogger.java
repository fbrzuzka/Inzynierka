/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Controllers;

import AutoPRK.views.mainWindow;

/**
 *
 * @author fbrzuzka
 */
public class PRKLogger {
    
    private mainWindow window;
    private PRKLogger() {
        this.window = mainWindow.window;
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
