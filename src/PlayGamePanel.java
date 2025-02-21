import javax.swing.*;
import java.awt.*;

class PlayGamePanel extends JPanel {
    public PlayGamePanel() {
        setLayout(new BorderLayout());
        JLabel playGameLabel = new JLabel("Game Panel - (Not implemented yet)", SwingConstants.CENTER);
        playGameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(playGameLabel, BorderLayout.CENTER);
    }
}