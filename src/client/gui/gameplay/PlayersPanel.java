package client.gui.gameplay;

import model.game.PlayerStat;

import javax.swing.*;
import java.awt.*;

public class PlayersPanel extends JPanel {

    public PlayersPanel(PlayerStat[] playerStats) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        setPlayerStats(playerStats);
    }

    public PlayersPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
    }

    private JPanel createPlayerBox(PlayerStat playerStat) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(120, 40));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(playerStat.username);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel statsLabel = new JLabel("Win: " + playerStat.winningGameNumber + "/" + playerStat.totalGameNumber + " avg: " + String.format("%.1f", playerStat.averageWinningTime) + "s");
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(statsLabel, BorderLayout.SOUTH);
        return panel;
    }

    public void setPlayerStats(PlayerStat[] playerStats) {
        removeAll();

        add(Box.createVerticalStrut(10));

        for (PlayerStat playerStat : playerStats) {
            add(createPlayerBox(playerStat));
            add(Box.createVerticalStrut(10));
        }

        revalidate();
        repaint();
    }
}
