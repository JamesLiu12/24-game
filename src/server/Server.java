package server;

import dao.OnlineUserDAO;
import dao.UserInfoDAO;
import model.OnlineUser;
import model.UserInfo;
import service.UserService;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;

public class Server extends UnicastRemoteObject implements UserService {
    UserInfoDAO userInfoDAO = new UserInfoDAO();
    OnlineUserDAO onlineUserDAO = new OnlineUserDAO();

    public static void main(String[] args) {
        try {
            Server server = new Server();
            System.setSecurityManager(new SecurityManager());
            Naming.rebind("service.UserService", server);
            System.out.println("Server started and ready for clients.");
        } catch (Exception e) {
            System.err.println("Exception thrown: " + e);
        }
    }

    public Server() throws RemoteException, SQLException, ClassNotFoundException {

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

            userInfoDAO.insert(new UserInfo(username, password));
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
