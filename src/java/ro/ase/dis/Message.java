/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dis;

import java.util.Objects;

/**
 *
 * @author costin1989
 */
public class Message {
    private String host;
    private String time;
    private String password;
    private String decryptedText;
    private int TTL;

    public Message(String host, String time, String password, String decryptedText, int TTL) {
        this.host = host;
        this.time = time;
        this.password = password;
        this.decryptedText = decryptedText;
        this.TTL = TTL;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecryptedText() {
        return decryptedText;
    }

    public void setDecryptedText(String decryptedText) {
        this.decryptedText = decryptedText;
    }

    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.host);
        hash = 31 * hash + Objects.hashCode(this.time);
        hash = 31 * hash + Objects.hashCode(this.password);
        hash = 31 * hash + Objects.hashCode(this.decryptedText);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.decryptedText, other.decryptedText)) {
            return false;
        }
        return true;
    }

}
