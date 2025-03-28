package client.gui.gameplay;

import client.gui.GameGUI;
import model.game.Card;
import math.ExpressionEvaluator;
import model.game.ValidationMessage;
import service.game.JoinGameService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ExpressionInputPanel extends JPanel {
    private final JTextField exprField;
    private final JLabel resultLabel;
    private List<Integer> cardNumbers = new ArrayList<>();
    final private Font largerFont;
    final private Font smallerFont;

    public ExpressionInputPanel(GameGUI gameGUI) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);

        largerFont = new Font("SansSerif", Font.PLAIN, 18);
        smallerFont = new Font("SansSerif", Font.PLAIN, 14);

        exprField = new JTextField("", 20);
        exprField.setFont(largerFont);

        resultLabel = new JLabel("");
        resultLabel.setFont(largerFont);

        exprField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        System.out.println("Send Validation Message");
                        gameGUI.gameService.sendValidationRequest(new ValidationMessage(gameGUI.username, exprField.getText()));
                    } catch (Exception err) {
                        System.out.println("fail to send Validation Request: " + err);
                    }
                }
            }
        });

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
        if (result == null) {
            resultLabel.setText("Invalid Input or Non-integer Result");
            resultLabel.setFont(smallerFont);
        } else {
            resultLabel.setText(" = " + result);
            resultLabel.setFont(largerFont);
        }
    }

    public void setCardNumbers(Card[] cards) {
        for (Card card : cards) {
            cardNumbers.add(card.value);
        }
    }
}
