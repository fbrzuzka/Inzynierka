/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi_Pi.Controllers.Raspberry;

import KuKuFi_Pi.Models.Model;

/**
 *
 * @author fbrzuzka
 */
public class Pin_Pi {

    String name;

    public Pin_Pi(String number) {
        this.name = "pin" + (Integer.parseInt(number) -1);
        System.out.println("utworzylem: " + name);
    }

    void low(){
        Model.getInstance().getOutputPin(name).low();
      //  System.out.println("pin Low");
    }

    void high(){
        Model.getInstance().getOutputPin(name).high();
     //   System.out.println("pin High");
    }

}
