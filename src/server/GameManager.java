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
import java.util.*;

public class GameManager {
    final private StartGameService startGameService;
    final private List<String> usernames;
    private volatile long firstJoinTime = 0;
    private long startTime;
    private long endTime;
    final private UserInfoDAO userInfoDAO;

    public GameManager(UserInfoDAO userInfoDAO) throws NamingException, JMSException, SQLException, ClassNotFoundException {
        startGameService = new StartGameService();
        usernames = new ArrayList<>();
        this.userInfoDAO = userInfoDAO;
    }

    public void start() throws SQLException, JMSException, InterruptedException {
        new Thread(this::listenForJoins).start();

        System.out.println("Server started and ready for clients.");

        while (true) {
            Thread.sleep(500);
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

                    Card[] cards = Card.generate4RandomCards();

                    System.out.println(cards);

                    GameStartMessage gameStartMessage = new GameStartMessage(
                            cards,
                            playerStats
                    );

                    startGameService.startGame(gameStartMessage);
                    startTime = System.currentTimeMillis();

                    List<Integer> cardNumbers = new ArrayList<>();
                    for (Card card : cards) {
                        cardNumbers.add(card.value);
                    }

                    while (true) {
                        System.out.println("Start to accept validation request");
                        ValidationMessage validationMessage = startGameService.acceptValidationRequest();
                        System.out.println("Message: " + validationMessage);
                        boolean allUsedOnce = ExpressionEvaluator.isAllUsedOnce(validationMessage.expression, cardNumbers);
                        Integer result = ExpressionEvaluator.eval(validationMessage.expression);
                        if (allUsedOnce && result != null && result == 24) {
                            endTime = System.currentTimeMillis();
                            startGameService.endGame(validationMessage);
                            firstJoinTime = 0;
                            updateAllPlayerStats(validationMessage.username);
                            System.out.println(usernames);
                            usernames.clear();
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
                System.out.println("Start to accept join request");
                String username = startGameService.acceptJoinRequest();

                synchronized (usernames) {
                    if (!usernames.contains(username)) {
                        usernames.add(username);
                        System.out.println("User enter");

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

    private void updateAllPlayerStats(String winnerUsername) throws SQLException {
        for (String username : usernames) {
           PlayerStat playerStat = userInfoDAO.getPlayerStat(username);

           if (Objects.equals(playerStat.username, winnerUsername)) {
               playerStat.averageWinningTime = (playerStat.gamesPlayed * playerStat.averageWinningTime
                       + (endTime - startTime) * 0.001) / (playerStat.gamesPlayed + 1);
               playerStat.gamesWon += 1;
           }

           playerStat.gamesPlayed += 1;
           userInfoDAO.updatePlayerStat(playerStat);
        }
    }
}
