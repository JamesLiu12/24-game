package client;

import client.gui.GameGUI;

import javax.swing.*;

public class JPoker24Game {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI(args[0]));
    }
}
