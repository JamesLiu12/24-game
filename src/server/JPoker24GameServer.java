package server;

import javax.jms.JMSException;
import javax.naming.NamingException;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;

public class JPoker24GameServer extends UnicastRemoteObject {

    final private LoginManager loginManager;
    final private GameManager gameManager;

    public static void main(String[] args) {
        try {
            JPoker24GameServer server = new JPoker24GameServer();
            System.setSecurityManager(new SecurityManager());
            Naming.rebind("service.remote.LoginService", server.loginManager);
            server.gameManager.start();
        } catch (Exception e) {
            System.err.println("Exception thrown: " + e);
        }
    }

    public JPoker24GameServer() throws RemoteException, SQLException, ClassNotFoundException, NamingException, JMSException {
        loginManager = new LoginManager();
        gameManager = new GameManager();
    }

}
