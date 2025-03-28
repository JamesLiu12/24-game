package model.game;

import java.io.Serializable;

public class ValidationMessage implements Serializable {
    public String username;
    public String expression;

    public ValidationMessage(String username, String expression) {
        this.username = username;
        this.expression = expression;
    }
}
