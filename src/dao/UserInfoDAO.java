package dao;

import java.sql.*;
import model.login.UserInfo;

public class UserInfoDAO extends BaseDAO implements DatabaseOperations<UserInfo> {
    public UserInfoDAO() throws SQLException, ClassNotFoundException {
        super();
        String sql = "CREATE TABLE IF NOT EXISTS UserInfo (\n" +
                "    username VARCHAR(255) PRIMARY KEY,\n" +
                "    password VARCHAR(255) NOT NULL,\n" +
                "    games_won INT NOT NULL,\n" +
                "    games_played INT NOT NULL,\n" +
                "    average_winning_time FLOAT NOT NULL\n" +
                ");";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void insert(UserInfo user) throws SQLException {
        String sql = "INSERT INTO UserInfo (username, password, games_won, games_played, average_winning_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.username);
            stmt.setString(2, user.password);
            stmt.setInt(3, user.gamesWon);
            stmt.setInt(4, user.gamesPlayed);
            stmt.setInt(5, user.averageWinningTime);
            stmt.executeUpdate();
            System.out.println("User added: " + user.username);
        }
    }

    @Override
    public UserInfo read(String username) throws SQLException {
        String sql = "SELECT * FROM UserInfo WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserInfo(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("games_won"),
                        rs.getInt("games_played"),
                        rs.getInt("average_winning_time"));
            }
        }
        return null;
    }

    @Override
    public void update(UserInfo user) throws SQLException {
        String sql = "UPDATE UserInfo SET password = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.password);
            stmt.setString(2, user.username);
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(String username) throws SQLException {
        String sql = "DELETE FROM UserInfo WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
