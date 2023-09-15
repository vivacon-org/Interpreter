package org.hungdoan.lang.ast;

public class Identifier extends Expression {

    private String symbol;

    public Identifier() {
        super(NodeType.IDENTIFIER);
    }

    public Identifier(String symbol) {
        super(NodeType.IDENTIFIER);
        this.symbol = symbol;
    }
}
