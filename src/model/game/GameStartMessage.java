package model.game;

import java.io.Serializable;
import java.util.List;

public class GameStartMessage implements Serializable {
    public Card[] cards;
    public List<PlayerStat> playerStats;

    public GameStartMessage(Card[] cards, List<PlayerStat> playerStats) {
        this.cards = cards;
        this.playerStats = playerStats;
    }
}
