package model;

public class PlayerStats {
    public String username;
    public float winningRate;
    public float averageWinningTime;

    public PlayerStats(String username, float winningRate, float averageWinningTime) {
        this.username = username;
        this.winningRate = winningRate;
        this.averageWinningTime = averageWinningTime;
    }
}
