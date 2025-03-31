package service.remote;

import model.login.UserInfo;

import java.rmi.*;

public interface LoginService extends Remote {
    boolean login(String username, String password) throws RemoteException;
    boolean register(String username, String password) throws RemoteException;
    void logout(String username) throws RemoteException;
}