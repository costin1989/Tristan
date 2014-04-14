package ro.ase.dis;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.jms.*;

@Stateful
public class MessageTaskSender {

    @Resource(mappedName = "jms/connectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/taskQueue")
    private Queue queue;
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
            try (Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE); 
                    MessageProducer messageProducer = session.createProducer(queue)) {
                textMessage = session.createTextMessage();
                textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);

                textMessage.setText(message);
                messageProducer.send(textMessage);
            }
        } catch (JMSException e) {
            System.err.println(e.getMessage());
        }
    }
}