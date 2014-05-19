/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.ase.dis.objects;

import java.util.Date;

/**
 *
 * @author costin1989
 */
public class EncryptionResponseObject implements ResponseObject{
    
    private String host;
    private Date timestamp;
    private String password;
    private String decrypted;


    public EncryptionResponseObject() {
    }

    public EncryptionResponseObject(String host, Date timestamp, String password, String decrypted) {
        this.host = host;
        this.timestamp = timestamp;
        this.password = password;
        this.decrypted = decrypted;
    }
   
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecrypted() {
        return decrypted;
    }

    public void setDecrypted(String decrypted) {
        this.decrypted = decrypted;
    }

    @Override
    public String toString() {
        return "EncryptionResponseObject{" + "host=" + host + ", timestamp=" + timestamp + ", password=" + password + ", decrypted=" + decrypted + '}';
    }
    
}
