package client.gui.gameplay;

import javax.swing.*;
import java.awt.*;

public class ExpressionInputPanel extends JPanel {

    public ExpressionInputPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);

        Font largerFont = new Font("SansSerif", Font.PLAIN, 18);

        JTextField exprField = new JTextField("(J+Q)+8/4", 20);
        exprField.setFont(largerFont);

        JLabel resultLabel = new JLabel(" = " + "25");
        resultLabel.setFont(largerFont);

        add(exprField);
        add(resultLabel);
    }
}
