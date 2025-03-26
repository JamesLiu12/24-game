package service.jms;

import javax.jms.*;
import javax.naming.NamingException;
import java.io.Serializable;

public class QueueSender extends BaseJMS {
    private QueueConnectionFactory queueConnectionFactory;
    private Queue queue;
    private MessageProducer producer;

    public QueueSender(String host, String connectionFactoryName, String destinationResourceName) throws NamingException, JMSException {
        super(host, connectionFactoryName, destinationResourceName);
        setup();
    }

    @Override
    protected void setup() throws JMSException {
        queueConnectionFactory = (QueueConnectionFactory) connectionFactory;
        queue = (Queue) destinationResource;
        connection = queueConnectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(queue);
        connection.start();
    }

    public void sendText(String payload) throws JMSException {
        TextMessage message = session.createTextMessage(payload);
        producer.send(message);
        System.out.println("Sent Text to Queue: " + payload);
    }

    public void sendObject(Serializable object) throws JMSException {
        ObjectMessage message = session.createObjectMessage(object);
        producer.send(message);
        System.out.println("Sent Object to Queue: " + object);
    }
}
