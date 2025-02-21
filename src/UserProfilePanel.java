import javax.swing.*;
import java.awt.*;

class UserProfilePanel extends JPanel {
    public UserProfilePanel() {
        setLayout(new BorderLayout());
        JTextArea profileInfo = new JTextArea("Kevin\nNumber of wins: 10\nNumber of games: 20\nAverage time to win: 12.5s\nRank: #10");
        profileInfo.setEditable(false);
        profileInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        add(profileInfo, BorderLayout.CENTER);
    }
}