package client.gui.gameplay;

import model.game.Card;
import service.game.ExpressionEvaluator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExpressionInputPanel extends JPanel {
    private final JTextField exprField;
    private final JLabel resultLabel;
    private List<Integer> cardNumbers = new ArrayList<>();

    public ExpressionInputPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);

        Font largerFont = new Font("SansSerif", Font.PLAIN, 18);

        exprField = new JTextField("", 20);
        exprField.setFont(largerFont);

        resultLabel = new JLabel("");
        resultLabel.setFont(largerFont);

        exprField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                updateResult();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                updateResult();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                updateResult();
            }
        });

        add(exprField);
        add(resultLabel);
    }

    private void updateResult() {
        String expr = exprField.getText();
        Integer result = ExpressionEvaluator.eval(expr, cardNumbers);
        System.out.println(result);
        resultLabel.setText(result == null ? "Invalid Input or Non-integer Result" : " = " + result);
    }

    public void setCardNumbers(Card[] cards) {
        for (Card card : cards) {
            cardNumbers.add(card.value);
        }
    }
}
