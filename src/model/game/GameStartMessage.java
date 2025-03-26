package model.game;

public class GameStartMessage {
    public Card[] cards;
    public PlayerStat[] playerStats;

    public GameStartMessage(Card[] cards, PlayerStat[] playerStats) {
        this.cards = cards;
        this.playerStats = playerStats;
    }
}
