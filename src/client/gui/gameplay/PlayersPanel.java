package client.gui.gameplay;

import javax.swing.*;
import java.awt.*;

public class PlayersPanel extends JPanel {

    public PlayersPanel(String[] playerNames) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        add(Box.createVerticalStrut(10));

        for (String name : playerNames) {
            add(createPlayerBox(name));
            add(Box.createVerticalStrut(10));
        }
    }

    private JPanel createPlayerBox(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(120, 40));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel statsLabel = new JLabel("Win: 0/0 avg: 0.0s");
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(statsLabel, BorderLayout.SOUTH);
        return panel;
    }
}
