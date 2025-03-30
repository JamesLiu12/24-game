package service.remote;

import model.game.PlayerStat;
import model.login.UserInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PlayerDataService extends Remote {
    PlayerStat getPlayerStat(String username) throws RemoteException;
    Integer getPlayerRank(String username) throws RemoteException;
    List<PlayerStat> getAllPlayerStats() throws RemoteException;
}