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
public class HashResponseObject implements ResponseObject{
    private String plainText;
    private String host;
    private Date timestamp;
    private String hashText;


    public HashResponseObject() {
    }

    public HashResponseObject(String plainText, String host, Date timestamp, String hashText) {
        this.plainText = plainText;
        this.host = host;
        this.timestamp = timestamp;
        this.hashText = hashText;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
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

    public String getHashText() {
        return hashText;
    }

    public void setHashText(String hashText) {
        this.hashText = hashText;
    }

    @Override
    public String toString() {
        return "HashResponseObject{" + "plainText=" + plainText + ", host=" + host + ", timestamp=" + timestamp + ", hashText=" + hashText + '}';
    }
    
}
