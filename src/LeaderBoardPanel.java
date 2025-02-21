import javax.swing.*;
import java.awt.*;

class LeaderBoardPanel extends JPanel {
    public LeaderBoardPanel() {
        setLayout(new BorderLayout());
        JTextArea leaderboardInfo = new JTextArea("1. Player 1 - 20 Wins\n2. Player 2 - 18 Wins\n3. Player 3 - 15 Wins\n...");
        leaderboardInfo.setEditable(false);
        leaderboardInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        add(leaderboardInfo, BorderLayout.CENTER);
    }
}
