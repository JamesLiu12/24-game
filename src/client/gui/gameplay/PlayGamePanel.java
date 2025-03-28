package client.gui.gameplay;

import client.gui.GameGUI;
import model.game.Card;
import model.game.GameStartMessage;
import model.game.PlayerStat;
import model.game.ValidationMessage;
import service.game.JoinGameService;

import javax.swing.*;
import java.awt.*;

public class PlayGamePanel extends JPanel {
    final private CardLayout panelLayout;
    final private JoinGameService gameService;
    final private GameBoardPanel gameBoardPanel;
    final private GameEndPanel gameEndPanel;

    public PlayGamePanel(GameGUI gameGUI) {
        gameService = gameGUI.gameService;

        JButton newGameButton = new JButton("New Game");

        JPanel waitingPanel = new JPanel(new GridBagLayout());
        JLabel waitingLabel = new JLabel("Waiting for players...");
        waitingPanel.add(waitingLabel);

        setLayout(panelLayout = new CardLayout());

        add(newGameButton, "NewGame");
        add(waitingPanel, "Waiting");
        add(gameBoardPanel = new GameBoardPanel(gameGUI), "GameBoard");
        add(gameEndPanel = new GameEndPanel(event -> JoinGame(gameGUI.username)), "GameEnd");

        newGameButton.addActionListener(event -> JoinGame(gameGUI.username));
    }

    private void JoinGame(String username) {
        panelLayout.show(this, "Waiting");

        try {
            gameService.joinGame(username, this::gameStartAction, this::gameEndAction);
        } catch (Exception e) {
            panelLayout.show(this, "NewGame");
            System.err.println("Failed joining game: " + e);
        }
    }

    private void gameStartAction(GameStartMessage gameStartMessage) {
        gameBoardPanel.setCards(gameStartMessage.cards);
        gameBoardPanel.setPlayerStats(gameStartMessage.playerStats);
        SwingUtilities.invokeLater(() -> panelLayout.show(this, "GameBoard"));
    }

    private void gameEndAction(ValidationMessage validationMessage) {
        gameEndPanel.setWinner(validationMessage.username);
        gameEndPanel.setSolution(validationMessage.expression);
        SwingUtilities.invokeLater(() -> panelLayout.show(this, "GameEnd"));
    }
}
