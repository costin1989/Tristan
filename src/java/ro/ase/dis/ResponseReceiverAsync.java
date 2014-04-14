/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.ase.dis;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
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

    public void sendMessage(String message) {
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

    @Override
    public void onMessage(Message m) {
        if (m != null) {
            if (m instanceof TextMessage) {
                TextMessage message = (TextMessage) m;
                
                try {
                    String stringMessage = message.getText();
                    String password = MessageChecker.processMessage(stringMessage);
                    if(password != null){
                        sendMessage(password);
                    }
                } catch (JMSException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }
}
