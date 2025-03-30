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
import javax.jms.TextMessage;
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
    final private Queue<ValidationMessage> validationMessageQueue;

    public StartGameService() throws NamingException, JMSException {
        topicSender = new TopicSender(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.TOPIC_JNDI);
        queueReceiver = new QueueReceiver(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.QUEUE_JNDI);
        playerQueue = new LinkedList<>();
        validationMessageQueue = new LinkedList<>();
    }

    public String acceptJoinRequest() throws JMSException {
        if (!playerQueue.isEmpty()) return playerQueue.poll();
        while (true) {
            Message message = queueReceiver.receive();
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                System.out.println("text: " + text);
                return text;
            } else {
                System.out.println("ValidationMessage add to queue");
                validationMessageQueue.offer((ValidationMessage) ((ObjectMessage) message).getObject());
            }
        }
    }

    public void startGame(GameStartMessage gameStartMessage) throws JMSException {
        topicSender.publishObject(gameStartMessage);
    }

    public ValidationMessage acceptValidationRequest() throws JMSException {
        if (!validationMessageQueue.isEmpty()) return validationMessageQueue.poll();
        while (true) {
            Message message = queueReceiver.receive();
            if (message instanceof ObjectMessage) {
                ValidationMessage validationMessage = (ValidationMessage) ((ObjectMessage) message).getObject();
                System.out.println("Validation Message: " + validationMessage);
                return validationMessage;
            } else {
                System.out.println("Player add to queue");
                playerQueue.offer(((TextMessage) message).getText());
            }
        }
    }

    public void endGame(ValidationMessage validationMessage) throws JMSException {
        topicSender.publishObject(validationMessage);
    }
}
