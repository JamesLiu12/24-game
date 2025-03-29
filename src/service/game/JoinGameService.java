package service.game;

import constant.JmsConfig;
import model.game.GameStartMessage;
import model.game.PlayerStat;
import model.game.ValidationMessage;
import service.jms.QueueSender;
import service.jms.TopicReceiver;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JoinGameService {
    final private QueueSender queueSender;
    final private TopicReceiver topicReceiver;
    final private List<String> playerUsernames;

    public JoinGameService() throws NamingException, JMSException {
        queueSender = new QueueSender(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.QUEUE_JNDI);
        topicReceiver = new TopicReceiver(JmsConfig.JMS_HOST, JmsConfig.CONNECTION_FACTORY_JNDI, JmsConfig.TOPIC_JNDI);
        playerUsernames = new ArrayList<>();
    }

    public void joinGame(String username, Consumer<GameStartMessage> gameStartCallback,
                         Consumer<ValidationMessage> gameEndCallback) throws JMSException {
        queueSender.sendText(username);

        topicReceiver.setMessageListener(message -> {
            try {
                if (message instanceof ObjectMessage) {
                    Serializable object = ((ObjectMessage) message).getObject();
                    System.out.println("Receive object: " + object);
                    if (object instanceof GameStartMessage) {
                        GameStartMessage gameStartMessage = (GameStartMessage) object;
                        for (PlayerStat playerStat : gameStartMessage.playerStats) {
                            if (playerStat.username.equals(username)) {
                                gameStartCallback.accept(gameStartMessage);

                                playerUsernames.clear();
                                for (PlayerStat playerStat1 : gameStartMessage.playerStats) {
                                    playerUsernames.add(playerStat1.username);
                                }

                                break;
                            }
                        }
                    } else if (object instanceof ValidationMessage) {
                        ValidationMessage validationMessage = (ValidationMessage) object;
                        if (playerUsernames.contains(validationMessage.username)) {
                            gameEndCallback.accept(validationMessage);
                        }
                    }
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendValidationRequest(ValidationMessage message) throws JMSException {
        queueSender.sendObject(message);
    }
}
