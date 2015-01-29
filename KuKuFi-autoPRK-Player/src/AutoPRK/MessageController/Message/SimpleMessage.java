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
public class SimpleMessage extends Message {

    private byte foo;

    public SimpleMessage(byte type, byte foo) {

        this.type = type;
        this.foo = foo;
    }

    public byte getFoo() {
        return foo;
    }

    public void setFoo(byte foo) {
        this.foo = foo;
    }

    @Override
    public String toString() {
        return Byte.toString(foo);
    }

    public byte toByte() {
        return foo;
    }

    

    @Override
    public byte[] toSend() {
        byte[] rc = new byte[58];
        int i = 0;
        rc[i++] = type;
        rc[i++] = foo;

        
        System.out.println(rc[0] + " " + rc[1] + " " + rc[2] + " " + rc[3] + " " + rc[4]);
        
        return rc;

    }

}
