package client.gui.gameplay;

import client.gui.GameGUI;
import service.game.GameService;

import javax.swing.*;
import java.awt.*;

public class PlayGamePanel extends JPanel {
    final private CardLayout panelLayout;
    final private GameService gameService;
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
