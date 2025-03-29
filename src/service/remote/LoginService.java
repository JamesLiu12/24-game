package service.remote;

import model.login.UserInfo;

import java.rmi.*;

public interface LoginService extends Remote {
    UserInfo login(String username, String password) throws RemoteException;
    UserInfo register(String username, String password) throws RemoteException;
    void logout(String username) throws RemoteException;
}