package model.game;

import java.io.Serializable;

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
}
