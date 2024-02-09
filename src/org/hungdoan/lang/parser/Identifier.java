package org.hungdoan.lang.parser;

import org.hungdoan.lang.lexer.Location;

public class Identifier extends Expression {

    private String symbol;

    public Identifier(String symbol, Location location) {
        super(NodeType.IDENTIFIER, location);
        this.symbol = symbol;
    }
}
