/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AutoPRK.MessageController.Message;

/**
 *
 * @author fbrzuzka
 */
public class DataMessage extends Message{

    private byte nrPalki;
    int numberOfNote;
    private int[] data;
    
    public DataMessage() {
    }

    
    public DataMessage(byte type, byte nrPalki, int[] data) {
    
        this.type = type;
        this.numberOfNote = numberOfNote;
        this.nrPalki = nrPalki;
        this.data = data;
    }
    
    
    @Override
    public String toString(){
        return  nrPalki + "  " +  datatoString(data);
    }
    
    
    public byte[] toSend(){
        byte[] rc = new byte[58];
        int i=0;
        int k=0;
        rc[i++] = type;
        rc[i++] = nrPalki;
        
        for (byte b : datatoString(data).getBytes()) {
            rc[i++] = b;
        }
       //  System.out.println("toSend: " + new String(rc));       
        return rc;
    }
    
    
    private String datatoString(int[] data) {
        String rc = "";
        String buf = "";
        for (int e : data) {
            buf = Integer.toString(e);
            if(buf.length() != 6){
                buf = fixIt(buf);
            }
            rc += buf;
            rc += ',';
        }
        
        return rc;
    }

    private String fixIt(String buf) {
        char[] rc = {'0', '0', '0', '0', '0', '0'};
        char[] temp = buf.toCharArray();
        
        for(int i=0 ; i < buf.length() ; i++){
            rc[6 - 1 - i] = temp[buf.length() - 1 - i];
        }
        String s = new String(rc);
        return s;
    }
    
    public int[] getData() {
        return data;
    }

    public byte getNrPalki() {
        return nrPalki;
    }

}
