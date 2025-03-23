package client.gui.gameplay;

import javax.swing.*;
import java.awt.*;

public class ExpressionInputPanel extends JPanel {

    public ExpressionInputPanel(String expression, String result) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);

        Font largerFont = new Font("SansSerif", Font.PLAIN, 18);

        JTextField exprField = new JTextField(expression, 20);
        exprField.setFont(largerFont);

        JLabel resultLabel = new JLabel(" = " + result);
        resultLabel.setFont(largerFont);

        add(exprField);
        add(resultLabel);
    }
}
