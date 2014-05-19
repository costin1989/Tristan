/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dis;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import ro.ase.dis.objects.EncryptionResponseObject;
import ro.ase.dis.objects.HashResponseObject;
import ro.ase.dis.objects.PrimeResponseObject;

/**
 *
 * @author costin1989
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/responseQueue")
})
public class ResponseReceiverAsync implements MessageListener {

    @Resource(mappedName = "jms/connectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/taskQueue")
    private Queue taskQueue;
    @Resource(mappedName = "jms/finalQueue")
    private Queue finalQueue;
    private Connection connection;

    public void connect() {
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (JMSException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void sendTask(String message) {
        TextMessage textMessage;
        try {
            connect();
            try (Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                    MessageProducer messageProducer = session.createProducer(taskQueue)) {
                textMessage = session.createTextMessage();
                textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

                textMessage.setText(message);
                messageProducer.send(textMessage);
            }
            disconnect();
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }

    private void sendFinal(String message) {
        TextMessage textMessage;
        try {
            connect();
            try (Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    MessageProducer messageProducer = session.createProducer(finalQueue)) {
                textMessage = session.createTextMessage();
                textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

                textMessage.setText(message);
                messageProducer.send(textMessage);
            }
            disconnect();
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onMessage(javax.jms.Message m) {
        if (m != null) {
            if (m instanceof ObjectMessage) {
                try {
                    ObjectMessage obj = (ObjectMessage) m;
                    if (obj.getObject() instanceof PrimeResponseObject) {
                        PrimeResponseObject encObj = (PrimeResponseObject) obj.getObject();
                        checkPrimeResponseMessage(encObj);
                    } else if (obj.getObject() instanceof EncryptionResponseObject) {
                        EncryptionResponseObject encObj = (EncryptionResponseObject) obj.getObject();
                        checkEncryptionResponseMessage(encObj);
                    } else if (obj.getObject() instanceof HashResponseObject) {
                        HashResponseObject encObj = (HashResponseObject) obj.getObject();
                        checkHashResponseMessage(encObj);
                    }
                } catch (JMSException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void checkEncryptionResponseMessage(EncryptionResponseObject encObj) {
        Message response = new Message(encObj.getHost(), encObj.getTimestamp().toString(), encObj.getPassword(), encObj.getDecrypted(), 0);
        Message result = MessageChecker.processMessage(response);
        if (result != null) {
            if (result.isFinalMessage()) {
                sendFinal(result.toString());
            } else {
                sendTask(result.getPassword());
            }
        }
    }
    
    private void checkPrimeResponseMessage(PrimeResponseObject encObj) {
        Message response = new Message(encObj.getHost(), encObj.getTimestamp().toString(), encObj.getDivisor(), encObj.getNumber(), 0);
        Message result = MessageChecker.processMessage(response);
        if (result != null) {
            if (result.isFinalMessage()) {
                sendFinal(result.toString());
            } else {
                sendTask(result.getPassword());
            }
        }
    }
    
    private void checkHashResponseMessage(HashResponseObject encObj) {
        Message response = new Message(encObj.getHost(), encObj.getTimestamp().toString(), encObj.getPlainText(), encObj.getHashText(), 0);
        Message result = MessageChecker.processMessage(response);
        if (result != null) {
            if (result.isFinalMessage()) {
                sendFinal(result.toString());
            } else {
                sendTask(result.getPassword());
            }
        }
    }
}
