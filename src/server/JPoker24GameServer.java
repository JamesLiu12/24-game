package server;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.SQLException;

public class JPoker24GameServer extends UnicastRemoteObject {

    final private UserManager userManager;

    public static void main(String[] args) {
        try {
            JPoker24GameServer server = new JPoker24GameServer();
            System.setSecurityManager(new SecurityManager());
            Naming.rebind("service.UserService", server.userManager);
            System.out.println("Server started and ready for clients.");
        } catch (Exception e) {
            System.err.println("Exception thrown: " + e);
        }
    }

    public JPoker24GameServer() throws RemoteException, SQLException, ClassNotFoundException {
        userManager = new UserManager();
    }

}
