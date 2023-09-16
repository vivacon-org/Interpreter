package org.hungdoan.lang;

public class Token {

    private String value;

    private TokenType type;

    private Location location;

    public Token(String value, TokenType type, int offset, int startLine, int startColumn) {
        this.value = value;
        this.type = type;
        this.location = new Location(offset, startLine, startColumn);
    }

    public Token(String value, TokenType type, Location location) {
        this.value = value;
        this.type = type;
        this.location = location;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", type=" + type +
                ", location=" + location.toString() +
                '}';
    }
}
