package client.gui.gameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEndPanel extends JPanel {
    private JLabel winnerLabel;
    private JLabel solutionLabel;
    private JButton nextGameButton;

    public GameEndPanel(ActionListener listener) {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        winnerLabel = new JLabel();
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winnerLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        solutionLabel = new JLabel();
        solutionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        solutionLabel.setFont(new Font("Arial", Font.BOLD, 24));

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(winnerLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(solutionLabel);
        centerPanel.add(Box.createVerticalGlue());

        add(centerPanel, BorderLayout.CENTER);

        nextGameButton = new JButton("Next game");
        nextGameButton.setPreferredSize(new Dimension(100, 30));
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(nextGameButton);
        add(bottomPanel, BorderLayout.SOUTH);

        nextGameButton.addActionListener(listener);
    }

    public void setWinner(String username) {
        winnerLabel.setText(username);
    }

    public void setSolution(String expression) {
        solutionLabel.setText(expression);
    }
}
