package server;

import dao.UserInfoDAO;
import model.game.PlayerStat;
import model.login.UserInfo;
import service.remote.PlayerDataService;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class PlayerDataManager extends UnicastRemoteObject implements PlayerDataService {
    final private UserInfoDAO userInfoDAO;

    public PlayerDataManager(UserInfoDAO userInfoDAO) throws RemoteException {
        this.userInfoDAO = userInfoDAO;
    }

    @Override
    public PlayerStat getPlayerStat(String username) throws SQLException {
        return userInfoDAO.getPlayerStat(username);
    }

    @Override
    public Integer getPlayerRank(String username) throws SQLException {
        return userInfoDAO.getRank(username);
    }

    @Override
    public List<PlayerStat> getAllPlayerStats(String username) throws SQLException {
        return userInfoDAO.getAllPlayerStats();
    }


}
