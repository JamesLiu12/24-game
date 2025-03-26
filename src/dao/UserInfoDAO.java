package dao;

import java.sql.*;
import model.login.UserInfo;

public class UserInfoDAO extends BaseDAO implements DatabaseOperations<UserInfo> {
    public UserInfoDAO() throws SQLException, ClassNotFoundException {
        super();
        String sql = "CREATE TABLE IF NOT EXISTS UserInfo (\n" +
                "    username VARCHAR(255) PRIMARY KEY,\n" +
                "    password VARCHAR(255) NOT NULL\n" +
                ");";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void insert(UserInfo user) throws SQLException {
        String sql = "INSERT INTO UserInfo (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.username);
            stmt.setString(2, user.password);
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
                return new UserInfo(rs.getString("username"), rs.getString("password"));
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
