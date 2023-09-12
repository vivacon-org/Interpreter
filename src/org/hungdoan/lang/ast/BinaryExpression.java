package org.hungdoan.lang.ast;

public class BinaryExpression extends Expression {
    private Expression left;

    private Expression right;

    private String operator;

    public BinaryExpression() {
        this.kind = NodeType.BINARY_EXPRESSION;
    }
}
