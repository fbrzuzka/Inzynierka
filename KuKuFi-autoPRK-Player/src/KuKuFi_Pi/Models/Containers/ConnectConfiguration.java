/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Models.Containers;

import KuKuFi_Pi.Controllers.PRKLogger;
import KuKuFi_Pi.Controllers.Raspberry.Pin_Pi;
import KuKuFi_Pi.Controllers.WindowController_Pi;
import KuKuFi_Pi.Models.DrumPart;
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
            WindowController_Pi.getInstance().setEnablingOfPlaying(true);
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

    public Pin_Pi getPinByDrumPart(DrumPart drumPart) {
        for (ConfPair pair : this) {
            if (pair.getDrumPart().getPartName().equals(drumPart.getPartName())) {
                return pair.getPin();
            }
        }
       throw new NullPointerException("Cannot find proper pin connected with: " + drumPart.getPartName());

    }

    
    
}
