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
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

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
            if (m instanceof TextMessage) {
                TextMessage message = (TextMessage) m;

                try {
                    String stringMessage = message.getText();
                    String[] messageTokens = stringMessage.split(",");
                    Message response = new Message(messageTokens[0], messageTokens[1], messageTokens[2], messageTokens[3], 0);
                    Message result = MessageChecker.processMessage(response);
                    if (result != null) {
                        if (result.isFinalMessage()) {
                            sendFinal(response.toString());
                        } else {
                            sendTask(response.getPassword());
                        }
                    }
                } catch (JMSException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
