package org.hungdoan.lang.ast;

public class Identifier extends Expression {

    private String symbol;

    public Identifier() {
        this.kind = NodeType.IDENTIFIER;
    }
}
