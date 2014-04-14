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

    private static List<Message> validMessages = new ArrayList<>();;
    private static List<Message> finalMessages = new ArrayList<>();;

    private static void addValidMessage(Message m) {
        validMessages.add(m);
    }

    private static void addFinalMessage(Message m) {
        finalMessages.add(m);
    }

    /**
     * 
     * @param s
     * @return null if everything is OK or password to send back in queue if it is not OK
     */
    public static String processMessage(String s) {
        String[] messageTokens = s.split(",");
        Message m = new Message(messageTokens[0], messageTokens[1], messageTokens[2], messageTokens[3], 0);
        //check if password and decrypted text are the same
        if (validMessages.contains(m)) {
            Message existingMessage = validMessages.get(validMessages.indexOf(m));
            if (existingMessage.getHost().equals(m.getHost())) {
                //send the password in queue again in order to be validated by other host
                existingMessage.setTTL(existingMessage.getTTL()+1);
                if(existingMessage.getTTL() > 5 ){
                    return null;
                }
                return m.getPassword();
            } else {
                addFinalMessage(m);
                validMessages.remove(m);
            }
        } else {
            addValidMessage(m);
        }
        return null;
    }

    public static List<Message> getFinalMessages() {
        return finalMessages;
    }
}
