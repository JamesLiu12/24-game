package server;

import dao.UserInfoDAO;
import math.ExpressionEvaluator;
import model.game.Card;
import model.game.GameStartMessage;
import model.game.PlayerStat;
import model.game.ValidationMessage;
import model.login.UserInfo;
import service.game.StartGameService;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GameManager {
    final private StartGameService startGameService;
    final private List<String> usernames;
    private volatile long firstJoinTime = 0;
    final private UserInfoDAO userInfoDAO;

    public GameManager(UserInfoDAO userInfoDAO) throws NamingException, JMSException, SQLException, ClassNotFoundException {
        startGameService = new StartGameService();
        usernames = new ArrayList<>();
        this.userInfoDAO = userInfoDAO;
    }

    public void start() throws SQLException, JMSException {
        new Thread(this::listenForJoins).start();

        System.out.println("Server started and ready for clients.");

        while (true) {
            long elapsed = System.currentTimeMillis() - firstJoinTime;

            synchronized (usernames) {
                if (usernames.size() >= 2 && elapsed >= 10000 || usernames.size() >= 4) {
                    ArrayList<PlayerStat> playerStats = new ArrayList<>();

                    for (String username : usernames) {
                        UserInfo userInfo = userInfoDAO.read(username);
                        playerStats.add(new PlayerStat(
                                userInfo.username,
                                userInfo.gamesWon,
                                userInfo.gamesPlayed,
                                userInfo.averageWinningTime));
                    }

                    Card[] cards = generate4RandomCards();

                    GameStartMessage gameStartMessage = new GameStartMessage(
                            cards,
                            playerStats
                    );

                    startGameService.startGame(gameStartMessage);


                    List<Integer> cardNumbers = new ArrayList<>();
                    for (Card card : cards) {
                        cardNumbers.add(card.value);
                    }

                    while (true) {
                        ValidationMessage validationMessage = startGameService.acceptValidationRequest();
                        System.out.println("Validation Message: " + validationMessage);
                        Integer result = ExpressionEvaluator.eval(validationMessage.expression, cardNumbers);
                        if (result != null && result == 24) {
                            startGameService.endGame(validationMessage);
                            usernames.clear();
                            firstJoinTime = 0;
                            break;
                        }
                    }

                }
            }

        }
    }

    public void listenForJoins() {
        while (true) {
            try {
                String username = startGameService.acceptJoinRequest();

                synchronized (usernames) {
                    if (!usernames.contains(username)) {
                        usernames.add(username);

                        if (usernames.size() == 1) {
                            firstJoinTime = System.currentTimeMillis();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private Card[] generate4RandomCards() {
        Random random = new Random();
        HashSet<String> seen = new HashSet<>();
        Card[] cards = new Card[4];

        int count = 0;
        while (count < 4) {
            int suit = random.nextInt(4) + 1;
            int value = random.nextInt(13) + 1;

            String key = suit + "-" + value;
            if (!seen.contains(key)) {
                seen.add(key);
                cards[count] = new Card(suit, value);
                count++;
            }
        }

        return cards;
    }
}
