/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.Models.Containers;

import AutoPRK.Controllers.PRKLogger;
import AutoPRK.Controllers.WindowController;
import AutoPRK.Models.DrumPart;
import java.util.ArrayList;

/**
 *
 * @author fbrzuzka
 */
public class ConnectConfiguration extends ArrayList<ConfPair> {

    public ConnectConfiguration() {
        super();

    }

    @Override
    public boolean add(ConfPair e) {
        
        boolean rc = super.add(e);
        PRKLogger.instance().logToInfoArea("dodałem do listy " + this.size() + "element");
        if (this.size() >= 1) {
            WindowController.getInstance().setEnablingOfStep4(true);
        }
        return rc;
    }

    public DrumPart getDrumPartByDrumKitElement(String s) {

        for (ConfPair pair : this) {
            if (pair.getDrumKitElement().equals(s)) {
                return pair.getDrumPart();
            }
        }
        throw new NullPointerException("Something gone terrible wrong in get drum blablabla... Check this immediatly!");

    }

    
    
}
