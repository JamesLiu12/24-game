package model.login;

import java.io.Serializable;

public class UserInfo implements Serializable {
    public String username;
    public String password;
    public int gamesWon;
    public int gamesPlayed;
    public int averageWinningTime;

    public UserInfo(String username, String password, int gamesWon, int gamesPlayed, int averageWinningTime) {
        this.username = username;
        this.password = password;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.averageWinningTime = averageWinningTime;
    }
}
