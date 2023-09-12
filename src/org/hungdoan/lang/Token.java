package org.hungdoan.lang;

public class Token {

    protected String value;

    protected TokenType type;

    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "{ type: " + getType() + " - Value: " + getValue() + " }";
    }
}
