package client.gui.gameplay;

import client.gui.GameGUI;
import model.game.Card;
import model.game.PlayerStat;
import service.game.JoinGameService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameBoardPanel extends JPanel {
    final private CardsPanel cardsPanel;
    final private PlayersPanel playersPanel;
    final private ExpressionInputPanel expressionInputPanel;

    public GameBoardPanel(GameGUI gameGUI) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(cardsPanel = new CardsPanel(), BorderLayout.CENTER);

        add(playersPanel = new PlayersPanel(), BorderLayout.EAST);

        add(expressionInputPanel = new ExpressionInputPanel(gameGUI), BorderLayout.SOUTH);
    }

    public void setCards(Card[] cards) {
        cardsPanel.setCards(cards);
        expressionInputPanel.setCardNumbers(cards);
    }

    public void setPlayerStats(List<PlayerStat> playerStats) {
        playersPanel.setPlayerStats(playerStats);
    }
}

