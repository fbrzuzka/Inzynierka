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
public abstract class AbstractMessage {
    
    protected byte type;
    
    public abstract byte[] toSend();
}
