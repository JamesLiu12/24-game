import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Server extends UnicastRemoteObject implements UserService {
    DatabaseManager databaseManager = new DatabaseManager();

    public static void main(String[] args) {
        try {
            Server server = new Server();
            System.setSecurityManager(new SecurityManager());
            Naming.rebind("UserService", server);
            System.out.println("Server started and ready for clients.");
        } catch (Exception e) {
            System.err.println("Exception thrown: " + e);
        }
    }

    public Server() throws RemoteException {
        databaseManager.clearOnlineUsers();
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        try {
            Map<String, String> users = databaseManager.loadUserInfo();
            Set<String> onlineUsers = databaseManager.loadOnlineUsers();

            if (!users.containsKey(username)
                    || !users.get(username).equals(password) || onlineUsers.contains(username)) {
                return false;
            }

            onlineUsers.add(username);
            databaseManager.updateOnlineUsers(onlineUsers);
            return true;
        } catch (IOException e) {
            throw new RemoteException("Login failed", e);
        }
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        try {
            Map<String, String> users = databaseManager.loadUserInfo();
            if (users.containsKey(username)) {
                return false;
            }

            databaseManager.saveUserInfo(username, password);
            Set<String> onlineUsers = databaseManager.loadOnlineUsers();
            onlineUsers.add(username);
            databaseManager.updateOnlineUsers(onlineUsers);
            return true;
        } catch (IOException e) {
            throw new RemoteException("Registration failed", e);
        }
    }

    @Override
    public void logout(String username) throws RemoteException {
        try {
            Set<String> onlineUsers = databaseManager.loadOnlineUsers();
            onlineUsers.remove(username);
            databaseManager.updateOnlineUsers(onlineUsers);
        } catch (IOException e) {
            throw new RemoteException("Logout failed", e);
        }
    }
}
