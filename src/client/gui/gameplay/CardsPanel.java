package client.gui.gameplay;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CardsPanel extends JPanel {

    public CardsPanel(List<Card> cards) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        add(Box.createVerticalGlue());

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        row.setOpaque(false);

        for (Card card : cards) {
            row.add(createCardImageLabel(card));
        }

        add(row);
        add(Box.createVerticalGlue());
    }

    private JLabel createCardImageLabel(Card card) {
        String path = card.getImagePath();
        ImageIcon originalIcon = new ImageIcon(path);

        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 145, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel label = new JLabel(scaledIcon);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return label;
    }
}
