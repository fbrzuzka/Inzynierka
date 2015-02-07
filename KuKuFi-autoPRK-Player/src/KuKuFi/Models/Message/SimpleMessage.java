/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KuKuFi.Models.Message;

/**
 *
 * @author fbrzuzka
 */
public class SimpleMessage extends AbstractMessage {

    private byte content;

    public SimpleMessage(byte type, byte foo) {

        this.type = type;
        this.content = foo;
    }

    public byte getFoo() {
        return content;
    }

    public void setFoo(byte foo) {
        this.content = foo;
    }

    @Override
    public String toString() {
        return Byte.toString(content);
    }

    public byte toByte() {
        return content;
    }

    

    @Override
    public byte[] toSend() {
        byte[] rc = new byte[58];
        int i = 0;
        rc[i++] = type;
        rc[i++] = content;

        
        System.out.println(rc[0] + " " + rc[1] + " " + rc[2] + " " + rc[3] + " " + rc[4]);
        
        return rc;

    }

}
