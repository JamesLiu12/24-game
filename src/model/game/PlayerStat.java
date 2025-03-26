package model.game;

public class PlayerStat {
    public String username;
    public int winningGameNumber;
    public int totalGameNumber;
    public float averageWinningTime;

    public PlayerStat(String username, int winningGameNumber, int totalGameNumber,  float averageWinningTime) {
        this.username = username;
        this.winningGameNumber = winningGameNumber;
        this.totalGameNumber = totalGameNumber;
        this.averageWinningTime = averageWinningTime;
    }
}
