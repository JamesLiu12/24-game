package service.game;

import constant.JmsConfig;
import model.game.GameStartMessage;
import model.game.PlayerStat;
import model.game.ValidationMessage;
import service.jms.QueueReceiver;
import service.jms.TopicSender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class StartGameService {
    final private TopicSender topicSender;
    final private QueueReceiver queueReceiver;
    final private Queue<String> playerQueue;

    public StartGameService() throws NamingException, JMSException {
        topicSender = new TopicSender(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.TOPIC_JNDI);
        queueReceiver = new QueueReceiver(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.QUEUE_JNDI);
        playerQueue = new LinkedList<>();
    }

    public String acceptJoinRequest() throws JMSException {
        if (!playerQueue.isEmpty()) return playerQueue.poll();
        return queueReceiver.receiveText();
    }

    public void startGame(GameStartMessage gameStartMessage) throws JMSException {
        topicSender.publishObject(gameStartMessage);
    }

    public ValidationMessage acceptValidationRequest() throws JMSException {
        while (true) {
            try {
                return (ValidationMessage) queueReceiver.receiveObject();
            } catch (JMSException e) {
                playerQueue.offer(queueReceiver.receiveText());
            }
        }
    }

    public void endGame(ValidationMessage validationMessage) throws JMSException {
        topicSender.publishObject(validationMessage);
    }
}
