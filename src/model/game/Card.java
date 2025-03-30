package model.game;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;

public class Card implements Serializable {
    public final int suit;      // 1 = ♣, 2 = ♠, 3 = ♦, 4 = ♥
    public final int value;

    public Card(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public String getImagePath() {
        return "images/card_" + suit + value + ".gif";
    }

    static public Card[] generate4RandomCards() {
        Random random = new Random();
        HashSet<String> seen = new HashSet<>();
        Card[] cards = new Card[4];

        int count = 0;
        while (count < 4) {
            int suit = random.nextInt(4) + 1;
            int value = random.nextInt(13) + 1;

            String key = suit + "-" + value;
            if (!seen.contains(key)) {
                seen.add(key);
                cards[count] = new Card(suit, value);
                count++;
            }
        }

        return cards;
    }
}
