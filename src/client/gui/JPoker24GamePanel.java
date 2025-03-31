package client.gui;

import client.gui.gameplay.PlayGamePanel;
import client.gui.leaderboard.LeaderBoardPanel;
import client.gui.profile.UserProfilePanel;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import static constant.GuiConfig.*;

class JPoker24GamePanel extends JPanel {
    final private CardLayout contentLayout;
    final private JPanel contentPanel;
    final private UserProfilePanel userProfilePanel;
    final private PlayGamePanel playGamePanel;
    final private LeaderBoardPanel leaderBoardPanel;

    public JPoker24GamePanel(GameGUI gameGUI) {
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(1, 4));

        JButton userProfileButton = new JButton("User Profile");
        JButton playGameButton = new JButton("Play Game");
        JButton leaderBoardButton = new JButton("Leader Board");
        JButton logoutButton = new JButton("Logout");

        menuPanel.add(userProfileButton);
        menuPanel.add(playGameButton);
        menuPanel.add(leaderBoardButton);
        menuPanel.add(logoutButton);

        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);

        contentPanel.add(userProfilePanel = new UserProfilePanel(gameGUI), "UserProfile");
        contentPanel.add(playGamePanel = new PlayGamePanel(gameGUI), "PlayGame");
        contentPanel.add(leaderBoardPanel = new LeaderBoardPanel(gameGUI), "LeaderBoard");

        userProfileButton.addActionListener(event -> {
            userProfilePanel.updateData();
            contentLayout.show(contentPanel, "UserProfile");
        });
        playGameButton.addActionListener(event -> contentLayout.show(contentPanel, "PlayGame"));
        leaderBoardButton.addActionListener(event -> {
            leaderBoardPanel.updateData();
            contentLayout.show(contentPanel, "LeaderBoard");
        });
        logoutButton.addActionListener(event -> {
            try {
                gameGUI.loginService.logout(gameGUI.username);
                gameGUI.loggedIn = false;
                gameGUI.showPanel("Login", LOGIN_PANEL_WIDTH, LOGIN_PANEL_HEIGHT);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(JPoker24GamePanel.this,
                        "Error connecting to the server.", "Remote Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(menuPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        contentLayout.show(contentPanel, "UserProfile");
    }

    public void init() {
        userProfilePanel.updateData();
    }
}