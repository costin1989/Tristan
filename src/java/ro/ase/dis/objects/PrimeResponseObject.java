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
public class PrimeResponseObject implements ResponseObject{
    private String number;
    private String host;
    private Date timestamp;
    private String divisor;


    public PrimeResponseObject() {
    }

    public PrimeResponseObject(String number, String host, Date timestamp, String divisor) {
        this.number = number;
        this.host = host;
        this.timestamp = timestamp;
        this.divisor = divisor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getDivisor() {
        return divisor;
    }

    public void setDivisor(String divisor) {
        this.divisor = divisor;
    }

    @Override
    public String toString() {
        return "PrimeResponseObject{" + "number=" + number + ", host=" + host + ", timestamp=" + timestamp + ", divisor=" + divisor + '}';
    }
    
}
