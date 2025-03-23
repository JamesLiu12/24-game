package client.gui;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

class RegisterPanel extends JPanel {
    public RegisterPanel(GameGUI gameGUI) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel registerFormPanel = new JPanel(new GridBagLayout());
        registerFormPanel.setBorder(BorderFactory.createTitledBorder("Register"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel loginLabel = new JLabel("Login Name");
        JTextField loginField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField(15);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        JPasswordField confirmPasswordField = new JPasswordField(15);
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        gbc.gridx = 0; gbc.gridy = 0;
        registerFormPanel.add(loginLabel, gbc);
        gbc.gridx = 1;
        registerFormPanel.add(loginField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        registerFormPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        registerFormPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        registerFormPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1;
        registerFormPanel.add(confirmPasswordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        registerButton.addActionListener(event -> {
            String username = loginField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterPanel.this,
                        "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(RegisterPanel.this,
                        "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    if (gameGUI.userService != null && gameGUI.userService.register(username, password)) {
                        JOptionPane.showMessageDialog(RegisterPanel.this,
                                "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        gameGUI.username = username;
                        gameGUI.showPanel("JPoker 24-Game", 600, 400);
                    } else {
                        JOptionPane.showMessageDialog(RegisterPanel.this,
                                "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RemoteException e) {
                    JOptionPane.showMessageDialog(RegisterPanel.this,
                            "Error connecting to the server.", "Remote Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> {
            gameGUI.showPanel("Login", 400, 300);
        });

        add(registerFormPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
