package KuKuFi_Pi.Models.Containers;

import java.util.LinkedHashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbrzuzka
 */
public class ProtocolListHashMap extends LinkedHashMap<String, SingleDrumElementTimelineArray> {

    public ProtocolListHashMap() {
        super();

    }

    @Override
    public String toString() {
        String rc = "\n";

        for (String dr : this.keySet()) {
            rc += dr + " (" +this.get(dr).size() + ") : " +  this.get(dr).toString() + "\n";
        }
        return rc;
    }

}
