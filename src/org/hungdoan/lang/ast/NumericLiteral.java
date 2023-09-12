package org.hungdoan.lang.ast;

public class NumericLiteral extends Expression {

    private int value;

    public NumericLiteral() {
        this.kind = NodeType.NUMERIC_LITERAL;
    }
}
