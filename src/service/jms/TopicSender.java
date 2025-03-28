package service.jms;

import javax.jms.*;
import javax.naming.NamingException;
import java.io.Serializable;

public class TopicSender extends BaseJMS {
    private TopicConnectionFactory topicConnectionFactory;
    private Topic topic;
    private TopicPublisher publisher;

    public TopicSender(String host, String connectionFactoryName, String destinationResourceName) throws NamingException, JMSException {
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
        publisher = topicSession.createPublisher(topic);
        connection.start();
    }

    public void publishText(String payload) throws JMSException {
        TextMessage message = session.createTextMessage(payload);
        publisher.publish(message);
        System.out.println("Published to Topic: " + payload);
    }

    public void publishObject(Serializable object) throws JMSException {
        ObjectMessage message = session.createObjectMessage(object);
        publisher.publish(message);
        System.out.println("Sent Object to Topic: " + object);
    }
}