package client.gui.gameplay;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class GameBoardPanel extends JPanel {

    public GameBoardPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        List<Card> cards = Arrays.asList(
                new Card(3, 11),
                new Card(3, 12),
                new Card(1, 8),
                new Card(4, 4)
        );

        add(new CardsPanel(cards), BorderLayout.CENTER);

        String[] playerNames = {"Kevin", "Kevin2", "Kevin2", "Kevin2"};
        add(new PlayersPanel(playerNames), BorderLayout.EAST);

        add(new ExpressionInputPanel("(J+Q)+8/4", "25"), BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new GameBoardPanel());
        frame.setVisible(true);
    }
}

