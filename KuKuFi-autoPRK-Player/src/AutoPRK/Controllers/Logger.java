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
public class Logger {
    
    private mainWindow window;
    private Logger() {
        this.window = mainWindow.window;
    }
    
    public static Logger instance() {
        return LoggerHolder.INSTANCE;
    }
    
    private static class LoggerHolder {

        private static final Logger INSTANCE = new Logger();
        
    }
    
    public void logToInfoArea(String str){
        window.getInfoArea().append(str);
    }
}
