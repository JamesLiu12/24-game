package model.game;

import java.io.Serializable;

public class PlayerStat implements Serializable {
    public String username;
    public int gamesWon;
    public int gamesPlayed;
    public float averageWinningTime;

    public PlayerStat(String username, int gamesWon, int gamesPlayed,  float averageWinningTime) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.averageWinningTime = averageWinningTime;
    }
}
