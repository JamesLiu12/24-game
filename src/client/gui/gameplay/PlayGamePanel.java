package client.gui.gameplay;

import client.gui.GameGUI;
import model.game.Card;
import model.game.PlayerStat;
import service.game.JoinGameService;

import javax.swing.*;
import java.awt.*;

public class PlayGamePanel extends JPanel {
    final private CardLayout panelLayout;
    final private JoinGameService gameService;
    final private GameBoardPanel gameBoardPanel;

    public PlayGamePanel(GameGUI gameGUI) {
        gameService = gameGUI.gameService;

        JButton newGameButton = new JButton("New Game");

        JPanel waitingPanel = new JPanel(new GridBagLayout());
        JLabel waitingLabel = new JLabel("Waiting for players...");
        waitingPanel.add(waitingLabel);

        setLayout(panelLayout = new CardLayout());

        add(newGameButton, "NewGame");
        add(waitingPanel, "Waiting");
        add(gameBoardPanel = new GameBoardPanel(), "GameBoard");

        newGameButton.addActionListener(event -> JoinGame(gameGUI.username));
    }

    private void JoinGame(String username) {
        panelLayout.show(this, "Waiting");

        gameBoardPanel.setCards(new Card[]{new Card(1, 1), new Card(1, 2), new Card(1, 2), new Card(1, 3)});
        gameBoardPanel.setPlayerStats(new PlayerStat[]{new PlayerStat("A", 1, 1, 1), new PlayerStat("A", 1, 1, 1), new PlayerStat("A", 1, 1, 1)});
        SwingUtilities.invokeLater(() -> panelLayout.show(this, "GameBoard"));
        try {
            gameService.joinGame(username, gameStartMessage -> {
                gameBoardPanel.setCards(gameStartMessage.cards);
                gameBoardPanel.setPlayerStats(gameStartMessage.playerStats);
                SwingUtilities.invokeLater(() -> panelLayout.show(this, "GameBoard"));
            });
        } catch (Exception e) {
            panelLayout.show(this, "NewGame");
            System.err.println("Failed joining game: " + e);
        }
    }
}
