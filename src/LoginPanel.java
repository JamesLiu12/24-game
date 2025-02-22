import javax.swing.*;
import java.awt.*;
import java.rmi.*;

class LoginPanel extends JPanel {

    public LoginPanel(GameGUI gameGUI) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel loginFormPanel = new JPanel(new GridBagLayout());
        loginFormPanel.setBorder(BorderFactory.createTitledBorder("Login"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel loginLabel = new JLabel("Login Name");
        JTextField loginField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        gbc.gridx = 0; gbc.gridy = 0;
        loginFormPanel.add(loginLabel, gbc);
        gbc.gridx = 1;
        loginFormPanel.add(loginField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        loginFormPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        loginFormPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        loginButton.addActionListener(event -> {
            String username = loginField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginPanel.this,
                        "Login name and password should not be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    if (gameGUI.userService != null && gameGUI.userService.login(username, password)) {
                        JOptionPane.showMessageDialog(LoginPanel.this,
                                "Logged in successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        gameGUI.username = username;
                        gameGUI.showPanel("JPoker 24-Game", 600, 400);
                    } else {
                        JOptionPane.showMessageDialog(LoginPanel.this,
                                "Invalid credentials or already logged in.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException e) {
                    JOptionPane.showMessageDialog(LoginPanel.this,
                            "Error connecting to the server.", "Remote Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(event -> {
            System.out.println("Navigating to Register Panel");
            gameGUI.showPanel("Register", 400, 300);
        });

        add(loginFormPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}