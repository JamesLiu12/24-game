import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class UserDBHandler {
    private static final String DB_HOST = "localhost";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";
    private static final String DB_NAME = "c3358";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME + "?useSSL=false";
    private Connection conn;

    public UserDBHandler() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    private void insert(String name, String birthday) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO c3358_2025 (name, birthday) VALUES (?, ?)");

            stmt.setString(1, name);
            stmt.setDate(2, java.sql.Date.valueOf(birthday));
            stmt.execute();

            System.out.println("Record created");

        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error inserting record: "+e);
        }

    }

    private void read(String name) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT birthday FROM c3358_2025 WHERE name = ?");
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                System.out.println("Birthday of "+name+" is on "+rs.getDate(1).toString());
            } else {
                System.out.println(name+" not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error reading record: "+e);
        }

    }

    private void list() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, birthday FROM c3358_2025");
            while(rs.next()) {
                System.out.println("Birthday of "+rs.getString(1)+" is on "+rs.getDate(2).toString());
            }
        } catch (SQLException e) {
            System.err.println("Error listing records: "+e);
        }

    }

    private void update(String name, String birthday) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE c3358_2025 SET birthday = ? WHERE name = ?");
            stmt.setDate(1, java.sql.Date.valueOf(birthday));
            stmt.setString(2, name);

            int rows = stmt.executeUpdate();
            if(rows > 0) {
                System.out.println("Birthday of "+name+" updated");
            } else {
                System.out.println(name+" not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error reading record: "+e);
        }

    }

    private void delete(String name) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM c3358_2025 WHERE name = ?");
            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            if(rows > 0) {
                System.out.println("Record of "+name+" removed");
            } else {
                System.out.println(name+" not found!");
            }
        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Error inserting record: "+e);
        }

    }
}
