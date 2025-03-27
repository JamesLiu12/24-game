package client.gui;

import client.gui.login.LoginPanel;
import client.gui.login.RegisterPanel;
import service.game.JoinGameService;
import service.remote.LoginService;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

public class GameGUI {
    final private JFrame frame;
    final private JPanel mainPanel;
    final private CardLayout cardLayout;

    final public LoginService loginService;
    final public JoinGameService gameService;

    public String username;

    public GameGUI(String host) throws RemoteException, NotBoundException, NamingException, JMSException {
        Registry registry = LocateRegistry.getRegistry(host);
        loginService = (LoginService)registry.lookup("service.remote.LoginService");

        gameService = new JoinGameService();

        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new RegisterPanel(this), "Register");
        mainPanel.add(new JPoker24GamePanel(this), "JPoker 24-Game");

        frame.add(mainPanel);
        frame.setVisible(true);

        showPanel("Login", 400, 300);
    }

    public void showPanel(String panelName, int width, int height) {
        cardLayout.show(mainPanel, panelName);
        frame.setTitle(panelName);
        frame.setSize(width, height);
    }
}
