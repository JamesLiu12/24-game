package service.jms;

import javax.jms.*;
import javax.naming.NamingException;
import java.io.Serializable;

public class QueueReceiver extends BaseJMS {
    private QueueConnectionFactory queueConnectionFactory;
    private Queue queue;
    private MessageConsumer queueReceiver;

    public QueueReceiver(String host, String connectionFactoryName, String destinationResourceName) throws NamingException, JMSException {
        super(host, connectionFactoryName, destinationResourceName);
        setup();
    }

    @Override
    protected void setup() throws JMSException {
        queueConnectionFactory = (QueueConnectionFactory) connectionFactory;
        queue = (Queue) destinationResource;
        connection = queueConnectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queueReceiver = session.createConsumer(queue);
        connection.start();
    }

    public String receiveText() throws JMSException {
        Message message = queueReceiver.receive();
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else {
            return null;
        }
    }

    public Serializable receiveObject() throws JMSException {
        Message message = queueReceiver.receive();
        if (message instanceof ObjectMessage) {
            return ((ObjectMessage) message).getObject();
        } else {
            return null;
        }
    }

    public Message receive() throws JMSException {
        return queueReceiver.receive();
    }
}
