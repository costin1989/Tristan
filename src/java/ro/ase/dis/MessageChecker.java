/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author costin1989
 */
public class MessageChecker {

    private static final List<Message> validMessages = new ArrayList<>();

    private static void addValidMessage(Message m) {
        validMessages.add(m);
    }

    /**
     *
     * @param m
     * @return the message back with the FinalMessage field filled in order to
     * send the message back to TaskQueue or to FinalQueue or null if there is
     * no action needed.
     */
    public static Message processMessage(Message m) {

        //check if password and decrypted text are the same
        if (validMessages.contains(m)) {
            Message existingMessage = validMessages.get(validMessages.indexOf(m));
            if (existingMessage.getHost().equals(m.getHost())) {
                //send the password in queue again in order to be validated by other host
                existingMessage.setTTL(existingMessage.getTTL() + 1);
                if (existingMessage.getTTL() > 5) {
                   existingMessage.setFinalMessage(true);
                } else {
                    existingMessage.setFinalMessage(false);
                }
            } else {
                validMessages.remove(m);
                existingMessage.setFinalMessage(true);
            }
            return existingMessage;
        } else {
            addValidMessage(m);
            return null;
        }
    }
}
