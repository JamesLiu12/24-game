package client;

import client.gui.GameGUI;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class JPoker24Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new GameGUI(args[0]);
            } catch (RemoteException | NotBoundException e) {
                System.err.println("Failed accessing RMI: " + e);
            } catch (NamingException | JMSException e) {
                System.err.println("Failed accessing JMS: " + e);
            }
        });
    }
}
