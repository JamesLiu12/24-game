package server;

import dao.OnlineUserDAO;
import dao.UserInfoDAO;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;

public class JPoker24GameServer extends UnicastRemoteObject {

    final private LoginManager loginManager;
    final private GameManager gameManager;
    final private UserInfoDAO userInfoDAO;
    final private OnlineUserDAO onlineUserDAO;
    final private PlayerDataManager playerDataManager;

    public static void main(String[] args) {
        try {
            JPoker24GameServer server = new JPoker24GameServer();
            System.setSecurityManager(new SecurityManager());
            Naming.rebind("service.remote.LoginService", server.loginManager);
            Naming.rebind("service.remote.PlayerDataService", server.playerDataManager);
            server.gameManager.start();
        } catch (Exception e) {
            System.err.println("Exception thrown: " + e);
        }
    }

    public JPoker24GameServer() throws RemoteException, SQLException, ClassNotFoundException, NamingException, JMSException {
        userInfoDAO = new UserInfoDAO();
        onlineUserDAO = new OnlineUserDAO();
        loginManager = new LoginManager(userInfoDAO, onlineUserDAO);
        gameManager = new GameManager(userInfoDAO);
        playerDataManager = new PlayerDataManager(userInfoDAO);
    }

}
