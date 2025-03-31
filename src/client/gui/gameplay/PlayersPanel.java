package client.gui.gameplay;

import model.game.PlayerStat;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlayersPanel extends JPanel {

    public PlayersPanel(List<PlayerStat> playerStats) {
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
        panel.setPreferredSize(new Dimension(180, 60));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(playerStat.username);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.WHITE);

        JLabel winStatLabel = new JLabel("Win: " + playerStat.gamesWon + "/" + playerStat.gamesPlayed);
        winStatLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        winStatLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel avgStatLabel = new JLabel("avg: " + String.format("%.1f", playerStat.averageWinningTime) + "s");
        avgStatLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        avgStatLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsPanel.add(winStatLabel);
        statsPanel.add(avgStatLabel);

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.SOUTH);
        return panel;
    }

    public void setPlayerStats(List<PlayerStat> playerStats) {
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
