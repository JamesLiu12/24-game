package service.game;

import constant.JmsConfig;
import model.game.GameStartMessage;
import model.game.PlayerStat;
import service.jms.QueueSender;
import service.jms.TopicReceiver;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import java.util.function.Consumer;

public class GameService {
    final private QueueSender queueSender;
    final private TopicReceiver topicReceiver;

    public GameService() throws NamingException, JMSException {
        queueSender = new QueueSender(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.QUEUE_JNDI);
        topicReceiver = new TopicReceiver(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.TOPIC_JNDI);
    }

    public void joinGame(String username, Consumer<GameStartMessage> callback) throws JMSException {
        queueSender.sendText(username);

        topicReceiver.setMessageListener(message -> {
            try {
                if (message instanceof ObjectMessage) {
                    GameStartMessage gameStartMessage = (GameStartMessage) ((ObjectMessage) message).getObject();
                    for (PlayerStat playerStat : gameStartMessage.playerStats) {
                        if (playerStat.username.equals(username)) {
                            callback.accept(gameStartMessage);
                            break;
                        }
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }
}
