import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

class JPoker24GamePanel extends JPanel {
    final private CardLayout contentLayout;
    final private JPanel contentPanel;

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

        contentPanel.add(new UserProfilePanel(), "UserProfile");
        contentPanel.add(new PlayGamePanel(), "PlayGame");
        contentPanel.add(new LeaderBoardPanel(), "LeaderBoard");

        userProfileButton.addActionListener(event -> contentLayout.show(contentPanel, "UserProfile"));
        playGameButton.addActionListener(event -> contentLayout.show(contentPanel, "PlayGame"));
        leaderBoardButton.addActionListener(event -> contentLayout.show(contentPanel, "LeaderBoard"));
        logoutButton.addActionListener(event -> {
            try {
                gameGUI.userService.logout(gameGUI.username);
                gameGUI.showPanel("Login", 400, 300);
            } catch (RemoteException e) {
                JOptionPane.showMessageDialog(JPoker24GamePanel.this,
                        "Error connecting to the server.", "Remote Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(menuPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        contentLayout.show(contentPanel, "UserProfile");
    }
}