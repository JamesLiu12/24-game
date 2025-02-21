import java.rmi.*;

public interface UserService extends Remote {
    boolean login(String username, String password) throws RemoteException;
    boolean register(String username, String password) throws RemoteException;
    void logout(String username) throws RemoteException;
}