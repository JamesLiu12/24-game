package client.gui;

import client.gui.login.LoginPanel;
import client.gui.login.RegisterPanel;
import model.login.UserInfo;
import service.game.JoinGameService;
import service.remote.LoginService;
import service.remote.PlayerDataService;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import static constant.GuiConfig.*;

public class GameGUI {
    final private JFrame frame;
    final private JPanel mainPanel;
    final private CardLayout cardLayout;

    final public LoginService loginService;
    final public JoinGameService gameService;
    final public PlayerDataService playerDataService;


    public boolean loggedIn;
    public String username;
    public UserInfo userInfo;

    final private LoginPanel loginPanel;
    final private RegisterPanel registerPanel;
    final private JPoker24GamePanel jPoker24GamePanel;

    public GameGUI(String host) throws RemoteException, NotBoundException, NamingException, JMSException {
        Registry registry = LocateRegistry.getRegistry(host);
        loginService = (LoginService)registry.lookup("service.remote.LoginService");

        gameService = new JoinGameService();

        playerDataService = (PlayerDataService)registry.lookup("service.remote.PlayerDataService");

        loggedIn = false;

        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (loggedIn && username != null) {
                        loginService.logout(username);
                    }
                } catch (RemoteException ex) {
                    System.err.println("Error during logout: " + ex.getMessage());
                }
            }
        });

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(loginPanel = new LoginPanel(this), "Login");
        mainPanel.add(registerPanel = new RegisterPanel(this), "Register");
        mainPanel.add(jPoker24GamePanel = new JPoker24GamePanel(this), "JPoker 24-Game");

        frame.add(mainPanel);
        frame.setVisible(true);

        showPanel("Login", LOGIN_PANEL_WIDTH, LOGIN_PANEL_HEIGHT);
    }

    public void showPanel(String panelName, int width, int height) {
        if (panelName.equals("JPoker 24-Game")) {
            jPoker24GamePanel.init();
        }
        cardLayout.show(mainPanel, panelName);
        frame.setTitle(panelName);
        frame.setSize(width, height);
    }
}
