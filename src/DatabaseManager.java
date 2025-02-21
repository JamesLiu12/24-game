import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DatabaseManager {
    private static final String USER_INFO_FILE = "UserInfo.txt";
    private static final String ONLINE_USER_FILE = "OnlineUser.txt";

    public void clearOnlineUsers() {
        try (PrintWriter writer = new PrintWriter(ONLINE_USER_FILE)) {
            writer.print("");
        } catch (IOException e) {
            System.err.println("Error clearing online users: " + e.getMessage());
        }
    }

    public synchronized Map<String, String> loadUserInfo() throws IOException {
        Map<String, String> users = new HashMap<>();
        File file = new File(USER_INFO_FILE);
        if (!file.exists()) file.createNewFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.put(parts[0], parts[1]);
            }
        }
        return users;
    }

    public synchronized void saveUserInfo(String username, String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_INFO_FILE, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        }
    }

    public synchronized Set<String> loadOnlineUsers() throws IOException {
        Set<String> onlineUsers = new HashSet<>();
        File file = new File(ONLINE_USER_FILE);
        if (!file.exists()) file.createNewFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                onlineUsers.add(line);
            }
        }
        return onlineUsers;
    }

    public synchronized void updateOnlineUsers(Set<String> users) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ONLINE_USER_FILE))) {
            for (String user : users) {
                writer.println(user);
            }
        }
    }
}
