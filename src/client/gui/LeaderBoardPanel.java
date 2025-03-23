package client.gui;

import javax.swing.*;
import java.awt.*;

public class LeaderBoardPanel extends JPanel {
    public LeaderBoardPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Rank", "Player", "Games won", "Games played", "Avg. winning time"};
        Object[][] data = {
                {1, "Player 1", 20, 40, "10.3s"},
                {2, "Player 2", 19, 49, "10.3s"},
                {3, "Player 3", 18, 48, "10.3s"},
                {4, "Player 4", 17, 47, "10.3s"},
                {5, "Player 5", 16, 46, "10.3s"},
                {6, "Player 6", 15, 45, "10.3s"},
                {7, "Player 7", 14, 44, "10.3s"},
                {8, "Player 8", 13, 43, "10.3s"},
                {9, "Player 9", 12, 42, "10.3s"},
                {10, "Player 10", 11, 41, "10.3s"}
        };

        JTable leaderboardTable = new JTable(data, columnNames);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 16));
        leaderboardTable.setRowHeight(25);
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        leaderboardTable.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        add(scrollPane, BorderLayout.CENTER);
    }
}
