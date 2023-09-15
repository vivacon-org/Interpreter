package org.hungdoan.lang.ast;

public class NumericLiteral extends Expression {

    private int value;

    public NumericLiteral() {
        super(NodeType.NUMERIC_LITERAL);
    }

    public NumericLiteral(int value) {
        super(NodeType.NUMERIC_LITERAL);
        this.value = value;
    }
}
