package service.remote;

import model.game.PlayerStat;
import model.login.UserInfo;

import java.rmi.Remote;
import java.sql.SQLException;
import java.util.List;

public interface PlayerDataService extends Remote {
    PlayerStat getPlayerStat(String username) throws SQLException;
    Integer getPlayerRank(String username) throws SQLException;
    List<PlayerStat> getAllPlayerStats(String username) throws SQLException;
}
