package org.hungdoan.lang.ast;

import org.hungdoan.lang.Location;

public class Identifier extends Expression {

    private String symbol;

    public Identifier(String symbol, Location location) {
        super(NodeType.IDENTIFIER, location);
        this.symbol = symbol;
    }
}
