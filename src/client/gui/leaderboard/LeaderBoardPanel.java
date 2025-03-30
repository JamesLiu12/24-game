package client.gui.leaderboard;

import client.gui.GameGUI;
import model.game.PlayerStat;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;

public class LeaderBoardPanel extends JPanel {
    final private GameGUI gameGUI;
    final private JTable leaderboardTable;

    public LeaderBoardPanel(GameGUI gameGUI) {
        this.gameGUI = gameGUI;

        setLayout(new BorderLayout());

        leaderboardTable = new JTable();
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 16));
        leaderboardTable.setRowHeight(25);
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        leaderboardTable.setShowGrid(true);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateData() {
        try {
            String[] columnNames = {"Rank", "Player", "Games won", "Games played", "Avg. winning time"};
            List<PlayerStat> allPlayerStats = gameGUI.playerDataService.getAllPlayerStats();

            Object[][] rowData = new Object[allPlayerStats.size()][5];
            for (int i = 0; i < allPlayerStats.size(); i++) {
                PlayerStat stat = allPlayerStats.get(i);
                rowData[i][0] = i + 1;
                rowData[i][1] = stat.username;
                rowData[i][2] = stat.gamesWon;
                rowData[i][3] = stat.gamesPlayed;
                rowData[i][4] = String.format("%.2f", stat.averageWinningTime);
            }

            leaderboardTable.setModel(new javax.swing.table.DefaultTableModel(rowData, columnNames));
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Failed to load user stats.",
                    "Remote Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
