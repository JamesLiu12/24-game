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
    public PlayerStat getPlayerStat(String username) throws RemoteException {
        try {
            return userInfoDAO.getPlayerStat(username);
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }

    @Override
    public Integer getPlayerRank(String username) throws RemoteException {
        try {
            return userInfoDAO.getRank(username);
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }

    @Override
    public List<PlayerStat> getAllPlayerStats() throws RemoteException {
        try {
            return userInfoDAO.getAllPlayerStats();
        } catch (SQLException e) {
            throw new RemoteException("Database error", e);
        }
    }

}
