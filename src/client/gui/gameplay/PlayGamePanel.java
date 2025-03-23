package client.gui.gameplay;

import javax.swing.*;
import java.awt.*;

public class PlayGamePanel extends JPanel {
    final private CardLayout panelLayout;

    public PlayGamePanel() {
        JButton newGameButton = new JButton("New Game");

        JPanel waitingPanel = new JPanel(new GridBagLayout());
        JLabel waitingLabel = new JLabel("Waiting for players...");
        waitingPanel.add(waitingLabel);

        setLayout(panelLayout = new CardLayout());

        add(newGameButton, "NewGame");
        add(waitingPanel, "Waiting");
        add(new GameBoardPanel(), "GameBoard");

        newGameButton.addActionListener(event -> JoinGame());
    }

    private void JoinGame() {
        panelLayout.show(this, "Waiting");
        panelLayout.show(this, "GameBoard");
    }
}
