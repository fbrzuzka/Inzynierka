package AutoPRK.Models;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import static javax.sound.midi.ShortMessage.NOTE_ON;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fbrzuzka
 */
public class ByteNote {

    public static char EMPTY_BYTE = (char) 0;

    private Model model = Model.instanceOf();
    private char charNote;

    public ByteNote(MidiEvent e) {
        charNote = parseEventToChar(e);
    }

    public ByteNote(char c) {
        charNote = c;
    }

    public void setByteNote(MidiEvent event) {
        
        double tickTimeMs = Math.round(event.getTick() * model.milis);

        MidiMessage message = event.getMessage();
        ShortMessage sm = null;
        try {
            sm = (ShortMessage) message;
            if (sm.getCommand() == NOTE_ON) {
                this.charNote = setBit(tickTimeMs, this.charNote);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       
    }

    private char parseEventToChar(MidiEvent event) {
        char rc = 0;

        double tickTimeMs = Math.round(event.getTick() * model.milis);

        MidiMessage message = event.getMessage();
        ShortMessage sm = null;
        try {
            sm = (ShortMessage) message;
            if (sm.getCommand() == NOTE_ON) {
                rc = setBit(tickTimeMs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rc;
    }

    private char setBit(double tickTimeMs) {
        char rc = 0;

        
        double tepoTicks = tickTimeMs * model.tempo;
        double moduloRest = tepoTicks % 1000;
        double dividedModuloRest = moduloRest / 125;
        int intModuloRest = (int) dividedModuloRest;
        int foo = (rc | 1 << intModuloRest);
        rc = (char) foo;

        return rc;
    }

    private char setBit(double tickTimeMs, char oldChar) {
       
        double tepoTicks = tickTimeMs * model.tempo;
        double moduloRest = tepoTicks % 1000;
        double dividedModuloRest = moduloRest / 125;
        int intModuloRest = (int) dividedModuloRest;
        int foo = (oldChar | 1 << intModuloRest);
       
        return (char) foo;
    }

    /**
     * @return the charNote
     */
    public char getCharNote() {
        return charNote;
    }

    /**
     * @param charNote the charNote to set
     */
    public void setCharNote(char charNote) {
        this.charNote = charNote;
    }

    @Override
    public String toString() {
        return String.valueOf((int) charNote);
    }

}
