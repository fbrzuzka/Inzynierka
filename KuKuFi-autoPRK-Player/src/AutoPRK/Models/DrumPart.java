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
    private String noteName = null;
    private int key;

    public DrumPart(int key, String partName) {
        this.partName = partName;
        this.key = key;
        this.noteName = toNoteName();
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
    
    /**
     * @return the key
     */
    public int getKey() {
        return key;
    }
    
    @Override
    public String toString() {
        return key + " " + noteName +  " " + partName;
    }

    private String toNoteName() {
        int octave = (key / 12) - 1;
        int note = key % 12;
        String noteName = Model.NOTE_NAMES[note];
        return noteName + octave;
    }


}
