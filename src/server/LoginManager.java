package server;

import dao.OnlineUserDAO;
import dao.UserInfoDAO;
import model.login.OnlineUser;
import model.login.UserInfo;
import service.remote.LoginService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class LoginManager extends UnicastRemoteObject implements LoginService {
    final private UserInfoDAO userInfoDAO;
    final private OnlineUserDAO onlineUserDAO;

    public LoginManager(UserInfoDAO userInfoDAO, OnlineUserDAO onlineUserDAO) throws RemoteException {
        this.userInfoDAO = userInfoDAO;
        this.onlineUserDAO = onlineUserDAO;
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        try {
            UserInfo userInfo = userInfoDAO.read(username);
            OnlineUser onlineUser = onlineUserDAO.read(username);

            if (userInfo == null || !userInfo.password.equals(password) || onlineUser != null) {
                return false;
            }

            onlineUserDAO.insert(new OnlineUser(userInfo.username));
            return true;
        } catch (SQLException e) {
            throw new RemoteException("Login failed", e);
        }
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        try {
            UserInfo userInfo = userInfoDAO.read(username);

            if (userInfo != null) {
                return false;
            }

            userInfoDAO.insert(new UserInfo(username, password, 0, 0, 0));
            onlineUserDAO.insert(new OnlineUser(username));
            return true;
        } catch (SQLException e) {
            throw new RemoteException("Registration failed", e);
        }
    }

    @Override
    public void logout(String username) throws RemoteException {
        try {
            onlineUserDAO.delete(username);
        } catch (SQLException e) {
            throw new RemoteException("Logout failed", e);
        }
    }
}
