package org.hungdoan.lang.v1.parser;

import org.hungdoan.lang.v1.lexer.Location;

public class Identifier extends Expression {

    private String symbol;

    public Identifier(String symbol, Location location) {
        super(NodeType.IDENTIFIER, location);
        this.symbol = symbol;
    }
}
