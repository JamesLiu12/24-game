package client.gui.profile;

import client.gui.GameGUI;
import model.game.PlayerStat;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UserProfilePanel extends JPanel {
    private final JLabel usernameLabel;
    private final JLabel winsLabel;
    private final JLabel gamesLabel;
    private final JLabel avgTimeLabel;
    private final JLabel rankLabel;
    private final GameGUI gameGUI;

    public UserProfilePanel(GameGUI gameGUI) {
        this.gameGUI = gameGUI;

        setLayout(new BorderLayout());

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        usernameLabel = new JLabel("");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        profilePanel.add(usernameLabel);

        winsLabel = new JLabel();
        winsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(winsLabel);

        gamesLabel = new JLabel();
        gamesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(gamesLabel);

        avgTimeLabel = new JLabel();
        avgTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(avgTimeLabel);

        rankLabel = new JLabel();
        rankLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        profilePanel.add(rankLabel);

        add(profilePanel, BorderLayout.CENTER);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        updateUserProfilePanel(); // Called when panel is shown
    }

    public void updateUserProfilePanel() {
        try {
            PlayerStat stat = gameGUI.playerDataService.getPlayerStat(gameGUI.username);
            Integer rank = gameGUI.playerDataService.getPlayerRank(gameGUI.username);

            usernameLabel.setText(stat.username);
            winsLabel.setText("Number of wins: " + stat.gamesWon);
            gamesLabel.setText("Number of games: " + stat.gamesPlayed);
            avgTimeLabel.setText(String.format("Average time to win: %.2f s", stat.averageWinningTime));
            rankLabel.setText("Rank: #" + (rank != null ? rank : "-"));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load user stats.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

