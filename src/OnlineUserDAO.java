import java.sql.*;

public class OnlineUserDAO extends BaseDAO implements DatabaseOperations<OnlineUser> {
    public OnlineUserDAO() throws SQLException, ClassNotFoundException {
        super();
        String sql = "CREATE TABLE IF NOT EXISTS OnlineUser (\n" +
                "    username VARCHAR(255) PRIMARY KEY,\n" +
                "    FOREIGN KEY (username) REFERENCES UserInfo(username)\n" +
                ")";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void insert(OnlineUser user) throws SQLException {
        String sql = "INSERT INTO OnlineUser (username) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.username);
            stmt.executeUpdate();
            System.out.println("User added to OnlineUser table: " + user.username);
        }
    }

    @Override
    public OnlineUser read(String username) throws SQLException {
        String sql = "SELECT * FROM OnlineUser WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new OnlineUser(rs.getString("username"));
            }
        }
        return null;
    }

    @Override
    public void update(OnlineUser user) {

    }

    @Override
    public void delete(String username) throws SQLException {
        String sql = "DELETE FROM OnlineUser WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
    }
}
