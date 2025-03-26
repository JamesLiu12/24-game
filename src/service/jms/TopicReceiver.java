package service.jms;

import javax.jms.*;
import javax.naming.NamingException;
import java.io.Serializable;

public class TopicReceiver extends BaseJMS {
    private TopicConnectionFactory topicConnectionFactory;
    private Topic topic;
    private TopicSubscriber subscriber;

    public TopicReceiver(String host, String connectionFactoryName, String destinationResourceName) throws NamingException, JMSException {
        super(host, connectionFactoryName, destinationResourceName);
        setup();
    }

    @Override
    protected void setup() throws JMSException {
        topicConnectionFactory = (TopicConnectionFactory) connectionFactory;
        topic = (Topic) destinationResource;
        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
        connection = topicConnection;
        TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        session = topicSession;
        subscriber = topicSession.createSubscriber(topic);
        connection.start();
    }

    public String receiveText() throws JMSException {
        Message message = subscriber.receive();
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else {
            return null;
        }
    }

    public Serializable receiveObject() throws JMSException {
        Message message = subscriber.receive();
        if (message instanceof ObjectMessage) {
            return ((ObjectMessage) message).getObject();
        } else {
            return null;
        }
    }
}
