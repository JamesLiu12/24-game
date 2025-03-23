package client.gui;

import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.*;

public class GameGUI {
    final private JFrame frame;
    final private JPanel mainPanel;
    final private CardLayout cardLayout;
    final public UserService userService;
    public String username;

    public GameGUI(String host) {
        UserService tempService = null;

        try {
            Registry registry = LocateRegistry.getRegistry(host);
            tempService = (UserService)registry.lookup("service.UserService");
        } catch (Exception e) {
            System.err.println("Failed accessing RMI: " + e);
        }

        userService = tempService;

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
