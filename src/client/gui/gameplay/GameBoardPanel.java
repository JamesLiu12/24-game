package client.gui.gameplay;

import model.game.Card;
import model.game.PlayerStat;

import javax.swing.*;
import java.awt.*;

public class GameBoardPanel extends JPanel {
    final private CardsPanel cardsPanel;
    final private PlayersPanel playersPanel;
    final private ExpressionInputPanel expressionInputPanel;

    public GameBoardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(cardsPanel = new CardsPanel(), BorderLayout.CENTER);

        add(playersPanel = new PlayersPanel(), BorderLayout.EAST);

        add(expressionInputPanel = new ExpressionInputPanel(), BorderLayout.SOUTH);
    }

    public void setCards(Card[] cards) {
        cardsPanel.setCards(cards);
        expressionInputPanel.setCardNumbers(cards);
    }

    public void setPlayerStats(PlayerStat[] playerStats) {
        playersPanel.setPlayerStats(playerStats);
    }
}

