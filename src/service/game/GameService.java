package service.game;

import constant.JmsConfig;
import service.jms.QueueSender;
import service.jms.TopicReceiver;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class GameService {
    final private QueueSender queueSender;
    final private TopicReceiver topicReceiver;

    public GameService() throws NamingException, JMSException {
        queueSender = new QueueSender(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.QUEUE_JNDI);
        topicReceiver = new TopicReceiver(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.TOPIC_JNDI);
    }

    public void joinGame(String playerId) throws JMSException {
        queueSender.sendText(playerId);
        topicReceiver.receiveObject();
    }
}
