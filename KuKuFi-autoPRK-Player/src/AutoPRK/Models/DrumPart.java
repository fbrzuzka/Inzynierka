package AutoPRK.Models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbrzuzka
 */
public class DrumPart {

    private String partName = null;

    public DrumPart(String partName) {
        this.partName = partName;
    }


    /**
     * @return the partName
     */
    public String getPartName() {
        return partName;
    }

    /**
     * @param partName the partName to set
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    @Override
    public String toString(){
        return partName;
    }


}
