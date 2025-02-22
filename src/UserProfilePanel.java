import javax.swing.*;
import java.awt.*;

public class UserProfilePanel extends JPanel {
    public UserProfilePanel() {
        setLayout(new BorderLayout());

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel username = new JLabel("Kevin");
        username.setFont(new Font("Arial", Font.BOLD, 24));
        profilePanel.add(username);

        String[] profileData = {
                "Number of wins: 10",
                "Number of games: 20",
                "Average time to win: 12.5s",
                "Rank: #10"
        };
        for (String line : profileData) {
            JLabel label = new JLabel(line);
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            profilePanel.add(label);
        }
        add(profilePanel, BorderLayout.CENTER);
    }
}
